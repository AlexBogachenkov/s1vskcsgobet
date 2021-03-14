package s1vskcsgobet.core.validators.team;

import org.springframework.stereotype.Component;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.requests.team.AddTeamRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AddTeamRequestValidator {

    private final TeamRepository teamRepository;

    public AddTeamRequestValidator(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<CoreError> validate(AddTeamRequest request) {
        List<CoreError> errors = new ArrayList<>();
        validateName(request).ifPresent(errors::add);
        return errors;
    }

    private Optional<CoreError> validateName(AddTeamRequest request) {
        if (request.getTeamName() == null || request.getTeamName().isBlank()) {
            return Optional.of(new CoreError("Team name", "must not be empty!"));
        } else if (teamRepository.existsByNameIgnoreCase(request.getTeamName())) {
            return Optional.of(new CoreError("Team name", "Team with this property already exists."));
        } else {
            return Optional.empty();
        }
    }

}
