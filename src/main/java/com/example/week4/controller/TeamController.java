package com.example.week4.controller;
import com.example.week4.model.Team;
import com.example.week4.repository.TeamRepository;
import com.example.week4.services.SuperRepository;
import com.example.week4.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
public class TeamController{
    @Autowired
    private SuperRepository superRepository;
//    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamService teamService;

    @Autowired
    public TeamController(SuperRepository superRepository, TeamRepository teamRepository) {
        this.superRepository = superRepository;
        this.teamRepository = teamRepository;
    }


    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public ResponseEntity<Team> getTeams() {
        return new ResponseEntity(
                superRepository.getTeams(),
                HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public Team findTeamById(@PathVariable("id") Long id) {
        return teamService.findTeamById(id);
    }


    @PostMapping("/createTeam")
    @ResponseStatus(HttpStatus.CREATED)
    public Team createTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }

    @PostMapping("/contestRegister")
    @ResponseStatus(HttpStatus.CREATED)
    public Team contestRegister(@RequestBody  Team team, @RequestParam  Long contestId) {
        return teamService.contestRegister(team,contestId);
    }

    @PutMapping(path="/editTeam/{teamId}")
    public Team editTeam(
            @PathVariable("teamId") Long teamId,
            @RequestBody Team team) {
        return teamService.editTeam(teamId,team);
    }

    @PutMapping(path="/setEditable/{teamId}/{writable}")
    public Team setEditable(
            @PathVariable("teamId") Long teamId,
            @PathVariable("writable") Boolean writable) {
        return teamService.setEditable(teamId,writable);
    }

    @PutMapping(path="/setReadOnly/{teamId}/{writable}")
    public Team setReadOnly(
            @PathVariable("teamId") Long teamId,
            @PathVariable("writable") Boolean writable) {
        return teamService.setReadOnly(teamId,writable);
    }


}
