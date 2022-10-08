package com.example.week4.services;

import com.example.week4.errors.CustomException;
import com.example.week4.model.Contest;
import com.example.week4.model.Person;
import com.example.week4.model.PromoteTeamDTO;
import com.example.week4.model.Team;
import com.example.week4.repository.TeamRepository;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class TeamService {
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ContestService contestService;

    @Autowired
    private PersonService personService;

    public Team findTeamById(@PathVariable("id") Long id) {
        Team team = teamRepository.findById(id).get();
        if (team.getName() != null) {
            return team;
        }
        return null;
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }


    public List<Team> findByContestId(Long contestId) {
        return teamRepository.findByContestId(contestId);
    }


    public Team contestRegister(Long teamId, Long contestId) {
        Contest contest = contestService.findContestById(contestId);
        Team team = checkTeamRegistration(teamRepository.findTeamById(teamId), contestId);
        team.setContest(contest);
        teamRepository.save(team);
        return team;
    }

    public Team checkTeamRegistration(Team team, Long contestId) {
        checkCoach(team);
        checkTeamMemberCount(team);
        contestService.checkAvailableCapacity(contestId);
        checkTeamMemberAge(team);
        checkDistinctMembers(team);
        checkTeamMembersInOtherTeam(team, contestId);
        return team;
    }

    public List<Team> findTeamsByContestantIdAndContestId(Long personId, Long contestId) {
        return teamRepository.findTeamsByContestantIdAndContestId(personId, contestId);
    }

    public void checkTeamMembersInOtherTeam(Team team, Long contestId) {
        List<Person> contestants = getAllTeamMember(team);
        Integer size;
        for (Person p : contestants) {
            size = teamRepository.findTeamsByContestantIdAndContestId(p.getId(), contestId).size();
            if (size >= 1) {
                throw new CustomException("Same person cannot be in multiple teams in the same contest");
            }
        }
    }

    public void checkDistinctMembers(Team team) {
        List<Person> allMembers = getAllMember(team);
        Set uniqueMembers = new HashSet(allMembers);
        if (allMembers.size() != uniqueMembers.size()) {
            throw new CustomException("Team must have unique team members");
        }
    }

    public void checkTeamMemberCount(Team team) {
        List<Person> teamMembers = getAllTeamMember(team);
        if (teamMembers.size() > 3) {
            throw new CustomException("The count of team members cannot be greater than 3");
        }

    }

    public void checkCoach(Team team) {
        if (team.getCoach() == null) {
            throw new CustomException("There is no coach in the team");
        }
    }

    public List<Person> getAllTeamMember(Team team) {
        List<Person> teamMembers = new ArrayList<Person>(team.getContestant());
        return teamMembers;
    }

    public List<Person> getAllMember(Team team) {
        List<Person> allMembers = new ArrayList<Person>(team.getContestant());
        allMembers.add(team.getCoach());
        return allMembers;
    }

    public void checkTeamMemberAge(Team team) {
        List<Person> teamMembers = getAllTeamMember(team);
        List<Date> birthDate = teamMembers.stream().map(i -> i.getBirthDate()).collect(Collectors.toList());
        List<Integer> actualAge = new ArrayList();
        for (Date d : birthDate) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            actualAge.add(2022 - cal.get(Calendar.YEAR));
        }
        for (int i = 0; i < actualAge.size(); i++) {
            if (actualAge.get(i) > 24) {
                throw new CustomException("Cannot register. Team member has age greater than 24.");
            }
        }
    }

    public Team editTeamName(@PathVariable("teamId") Long teamId, String name) {
        Team team = findTeamById(teamId);

        if (team.getContest() == null || team.getContest().getWritable() == true) {
           team.setName(name);
            teamRepository.save(team);
        }

        if (team.getContest().getWritable() == false) {
            throw new CustomException("The contest writable is set to false. You cannot edit the team info");
        }
      return team;
    }


    public Team promoteTeam(@RequestBody PromoteTeamDTO promoteTeamDTO) throws Exception {
        Long contestId = promoteTeamDTO.getContestId();
        if (contestId == null) {
            throw new CustomException("Null ContestId");
        }

        Long teamId = promoteTeamDTO.getTeamId();
        if (teamId == null) {
            throw new CustomException("Null teamId");
        }

        Contest contest = contestService.findContestById(contestId);
        Team team = teamRepository.findTeamById(teamId);


        if (team.getRank() == null) {
            throw new CustomException("Cannot promote. Null rank");
        }

        if (!(team.getRank() > 0 && team.getRank() < 6)) {
            throw new CustomException("Cannot promote. Rank is not in the range of 1-5." + " The teamrank is " + team.getRank());
        }

        Long superContestId = contest.getSuperContestId();
        if (superContestId == null) {
            throw new CustomException("Cannot promote. No superContest associated with this contest.");
        }

        Integer availableContestCapacity =
                contestService.findContestById(superContestId).getCapacity() - contestService.exhaustedCapacity(superContestId);
        if (!(availableContestCapacity > 0)) {
            throw new CustomException("Cannot promote. Out of superContest Capacity");
        }

        checkCoach(team);
        checkDistinctMembers(team);
        checkTeamMemberAge(team);
        checkTeamMembersInOtherTeam(team, superContestId);
        checkDistinctMembers(team);
        Team promotedTeam = promotedTeam(team, contestId);
        teamRepository.save(promotedTeam);
        return promotedTeam;
    }

    public Team promotedTeam(Team team, long contestId){
        Team promotedTeam = new Team();
        Contest contest = contestService.findContestById(contestId);
        promotedTeam.setName(team.getName());
        promotedTeam.setRank(team.getRank());
        promotedTeam.setState(team.getState());
        promotedTeam.setPromotedTeamId(team.getId());
        promotedTeam.setCoach(team.getCoach());
        promotedTeam.setContest(contestService.findContestById(contest.getSuperContestId()));
        promotedTeam.setPromotedFromContestId(contestId);
        for (Person p : team.getContestant()){
            promotedTeam.getContestant().add(p);
        }
        return promotedTeam;
    }
}


