package com.example.week4.services;

import com.example.week4.model.Contest;
import com.example.week4.model.ContestDto;
import com.example.week4.model.Team;
import com.example.week4.repository.ContestRepository;
import com.example.week4.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static jdk.nashorn.internal.objects.NativeDebug.map;

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

    private ContestDto buildDto(Contest contest) {
        return new ContestDto(contest.getName(), contest.getCapacity(), contest.getDate(), contest.getRegistration_allowed(),
                contest.getRegistration_to(), contest.getRegistration_from());
    }
    @Transactional
    public ContestDto updateFromDto(Long contestId, ContestDto dto) {
        Contest contest = findContestById(contestId);
        map(contest, dto);
        contestRepository.save(contest);
        return buildDto(contest);
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


    public Contest setEditable(@PathVariable("contestId") Long contestId, Boolean writable){
        Contest contest = findContestById(contestId);
        contest.setWritable(writable);
        contestRepository.save(contest);
        return contest;
    }
    public Contest setReadOnly(@PathVariable("contestId") Long contestId, Boolean writable){
        Contest contest = findContestById(contestId);
        contest.setWritable(writable);
        contestRepository.save(contest);
        return contest;
    }



}
