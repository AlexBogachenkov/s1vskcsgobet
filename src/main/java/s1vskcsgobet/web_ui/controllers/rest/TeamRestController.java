package s1vskcsgobet.web_ui.controllers.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import s1vskcsgobet.core.requests.team.AddTeamRequest;
import s1vskcsgobet.core.requests.team.DeleteTeamByNameRequest;
import s1vskcsgobet.core.responses.team.AddTeamResponse;
import s1vskcsgobet.core.responses.team.DeleteTeamByNameResponse;
import s1vskcsgobet.core.responses.team.FindAllTeamsResponse;
import s1vskcsgobet.core.services.TeamService;

@RestController
@RequestMapping("/team")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
public class TeamRestController {

    private final TeamService teamService;

    public TeamRestController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public AddTeamResponse addTeam(@RequestBody AddTeamRequest request) {
        return teamService.add(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{name}", produces = "application/json")
    public DeleteTeamByNameResponse deleteTeamByName(@PathVariable String name) {
        DeleteTeamByNameRequest request = new DeleteTeamByNameRequest();
        request.setTeamName(name);
        return teamService.deleteByName(request);
    }

    @GetMapping(produces = "application/json")
    public FindAllTeamsResponse findAllTeams() {
        return teamService.findAll();
    }

}
