package com.example.week4.services;
import com.example.week4.errors.CustomException;
import com.example.week4.model.Contest;
import com.example.week4.repository.ContestRepository;
import com.example.week4.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ContestService {

    @Autowired
    private ContestRepository contestRepository;

    @Autowired TeamRepository teamRepository;


    public Contest findContestById(@PathVariable("id") Long id) {
        Contest contest = contestRepository.findById(id).get();
        if (contest.getName() != null) {
            return contest;
        }
        return null;
    }


    public Contest createContest(Contest contest){
        return contestRepository.save(contest);
    }

    public Integer exhaustedCapacity(Long contestId){
        List<String> teamsInContest = teamRepository.findByContestId(contestId).stream().map(i -> i.getName()).collect(Collectors.toList());
        return  teamsInContest.size();
    }

    public void checkAvailableCapacity(Long contestId){
        Integer availableContestCapacity =
                findContestById(contestId).getCapacity() - exhaustedCapacity(contestId);
        if (!(availableContestCapacity > 0)) {
            throw new CustomException("Contest capacity limit reached.");
        }
    }



    public Contest editContest(@PathVariable("contestId") Long contestId,
                                      @RequestBody Contest contest) {
        Contest newContest = new Contest();
        Optional<Contest> oldContest = contestRepository.findById(contestId);
        if (oldContest.isPresent()) {
            if(oldContest.get().getWritable()==true)
            {
                contest.setId(contestId);
                newContest = contestRepository.save(contest);
            }
            else {
                return null;
            }
        }
        return newContest;
    }


    public Contest setEditable(@PathVariable("contestId") Long contestId){
        Contest contest = findContestById(contestId);
        contest.setWritable(true);
        contestRepository.save(contest);
        return contest;
    }

    public Contest setReadOnly( @PathVariable("contestId") Long contestId){
        Contest contest = findContestById(contestId);
        contest.setWritable(false);
        contestRepository.save(contest);
        return contest;
    }

    public Contest editName(@PathVariable("contestId") Long contestId,
                            @RequestParam("name") String name){
        Contest contest = findContestById(contestId);
        if (contest.getName()==null){
            throw new CustomException("The contest does not exist");
        }
        if (contest.getWritable()==false){
            throw new CustomException("The contest is not editable. Please set the writable flag to true");
        }
        contest.setName(name);
        contestRepository.save(contest);
        return contest;
    }



}
