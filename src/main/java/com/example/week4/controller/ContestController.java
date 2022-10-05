package com.example.week4.controller;

import com.example.week4.model.Contest;
import com.example.week4.model.ContestDto;
import com.example.week4.model.Team;
import com.example.week4.repository.ContestRepository;
import com.example.week4.services.ContestService;
import com.example.week4.services.SuperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contest")
public class ContestController {
    @Autowired
    private SuperRepository superRepository;
    @Autowired
    private ContestRepository contestRepository;
    @Autowired
    private ContestService contestService;



    @RequestMapping(value = "/contests", method = RequestMethod.GET)
    public ResponseEntity<Contest> getContests() {
        return new ResponseEntity(
                superRepository.getContests(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Contest findContestById(@PathVariable("id") Long id) {
        return contestService.findContestById(id);
    }


    @PostMapping("/createContest")
    @ResponseStatus(HttpStatus.CREATED)
    public Contest createContest(@RequestBody Contest contest) {
        return contestService.createContest(contest);
    }



    @PutMapping(path="/editContest/{contestId}")
    public Contest editContest(
            @PathVariable("contestId") Long contestId,
            @RequestBody Contest contest) {
        return contestService.editContest(contestId,contest);
    }

    @PutMapping(path="/setEditable/{contestId}")
    public Contest setEditable(
            @PathVariable("contestId") Long contestId
           ) {
        return contestService.setEditable(contestId);
    }


    @PutMapping(path="/setReadOnly/{contestId}")
    public Contest setReadOnly(
            @PathVariable("contestId") Long contestId){
       return contestService.setReadOnly(contestId);
    }

    @PutMapping(path="/editName/{contestId}")
    public Contest editName(
            @PathVariable("contestId") Long contestId,
            @RequestParam("name") String name){
        return contestService.editName(contestId, name);
    }


}
