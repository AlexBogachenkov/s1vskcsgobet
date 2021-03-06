package s1vskcsgobet.core.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.domain.Team;
import s1vskcsgobet.core.requests.team.AddTeamRequest;
import s1vskcsgobet.core.requests.team.DeleteTeamByNameRequest;
import s1vskcsgobet.core.responses.team.AddTeamResponse;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.team.DeleteTeamByNameResponse;
import s1vskcsgobet.core.responses.team.FindAllTeamsResponse;
import s1vskcsgobet.core.validators.team.AddTeamRequestValidator;
import s1vskcsgobet.core.validators.team.DeleteTeamByNameRequestValidator;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final AddTeamRequestValidator addTeamRequestValidator;
    private final DeleteTeamByNameRequestValidator deleteTeamByNameRequestValidator;

    public TeamService(TeamRepository teamRepository, AddTeamRequestValidator addTeamRequestValidator, DeleteTeamByNameRequestValidator deleteTeamByNameRequestValidator) {
        this.teamRepository = teamRepository;
        this.addTeamRequestValidator = addTeamRequestValidator;
        this.deleteTeamByNameRequestValidator = deleteTeamByNameRequestValidator;
    }

    public AddTeamResponse add(AddTeamRequest request) {
        List<CoreError> errors = addTeamRequestValidator.validate(request);
        if (!errors.isEmpty()) {
            return new AddTeamResponse(errors);
        }
        Team team = new Team(request.getTeamName());
        Team addedTeam = teamRepository.save(team);
        return new AddTeamResponse(addedTeam);
    }

    @Transactional
    public DeleteTeamByNameResponse deleteByName(DeleteTeamByNameRequest request) {
        List<CoreError> errors = deleteTeamByNameRequestValidator.validate(request);
        if (!errors.isEmpty()) {
            return new DeleteTeamByNameResponse(errors);
        }
        int deletedTeamCount = teamRepository.deleteByNameIgnoreCase(request.getTeamName());
        return new DeleteTeamByNameResponse(deletedTeamCount > 0);
    }

    public FindAllTeamsResponse findAllTeams() {
        List<Team> allTeams = teamRepository.findAll();
        return new FindAllTeamsResponse(allTeams);
    }

}
