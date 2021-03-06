package s1vskcsgobet.web_ui.controllers.rest;

import org.springframework.web.bind.annotation.*;
import s1vskcsgobet.core.requests.team.AddTeamRequest;
import s1vskcsgobet.core.requests.team.DeleteTeamByNameRequest;
import s1vskcsgobet.core.responses.team.AddTeamResponse;
import s1vskcsgobet.core.responses.team.DeleteTeamByNameResponse;
import s1vskcsgobet.core.responses.team.FindAllTeamsResponse;
import s1vskcsgobet.core.services.TeamService;

@RestController
@RequestMapping("/team")
public class TeamRestController {

    private final TeamService teamService;

    public TeamRestController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping(path = "/add",
            consumes = "application/json",
            produces = "application/json")
    public AddTeamResponse addTeam(@RequestBody AddTeamRequest request) {
        return teamService.add(request);
    }

    @PostMapping(path = "/deleteByName",
            consumes = "application/json",
            produces = "application/json")
    public DeleteTeamByNameResponse deleteTeamByName(@RequestBody DeleteTeamByNameRequest request) {
        return teamService.deleteByName(request);
    }

    @GetMapping(path = "/findAll",
            produces = "application/json")
    public FindAllTeamsResponse findAllTeams() {
        return teamService.findAllTeams();
    }

}
