package com.example.week4.services;

import com.example.week4.model.Contest;
import com.example.week4.model.Person;
import com.example.week4.model.Team;
import com.example.week4.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ContestService contestService;

    public Team findTeamById(@PathVariable("id") Long id) {
        Team team = teamRepository.findById(id).get();
        if (team.getName() != null) {
            return team;
        }
        return null;
    }
    public Team createTeam(Team team){
        return teamRepository.save(team);
    }


    public List<Team> findByContestId(Long contestId) {
        return teamRepository.findByContestId(contestId);
    }


    public Team contestRegister(Team team, Long contestId){
        Contest contest = contestService.findContestById(contestId);
        Integer actualCapacity = contest.getCapacity();
        Integer exhaustedCapacity = contestService.exhaustedCapacity(contestId);
        Integer availableCapacity =  actualCapacity -  exhaustedCapacity;
        final int ZERO = 0;

       if(availableCapacity > ZERO && availableCapacity <  actualCapacity){
           team.setContest(contest);
           teamRepository.save(team);
       }
      return team;
    }


    public Team editTeam(@PathVariable("teamId") Long teamId,
                               @RequestBody Team team) {
        Team newTeam = new Team();
        Optional<Team> oldTeam = teamRepository.findById(teamId);
        if (oldTeam.isPresent()) {
            if(oldTeam.get().getWritable()==true)
            {
                team.setId(teamId);
                newTeam = teamRepository.save(team);
            }
            else {
                return null;
            }
        }
        return newTeam;
    }


    public Team setEditable(@PathVariable("contestId") Long teamId, Boolean writable){
        Team team = findTeamById(teamId);
        team.setWritable(writable);
        teamRepository.save(team);
        return team;
    }
    public Team setReadOnly(@PathVariable("contestId") Long teamId, Boolean writable){
        Team team = findTeamById(teamId);
        team.setWritable(writable);
        teamRepository.save(team);
        return team;
    }
}


