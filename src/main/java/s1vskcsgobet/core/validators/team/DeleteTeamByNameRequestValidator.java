package s1vskcsgobet.core.validators.team;

import org.springframework.stereotype.Component;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.requests.team.DeleteTeamByNameRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DeleteTeamByNameRequestValidator {

    private final TeamRepository teamRepository;

    public DeleteTeamByNameRequestValidator(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<CoreError> validate(DeleteTeamByNameRequest request) {
        List<CoreError> errors = new ArrayList<>();
        validateName(request).ifPresent(errors::add);
        return errors;
    }

    private Optional<CoreError> validateName(DeleteTeamByNameRequest request) {
        if (request.getTeamName() == null || request.getTeamName().isBlank()) {
            return Optional.of(new CoreError("Team name", "must not be empty!"));
        } else if (!teamRepository.existsByNameIgnoreCase(request.getTeamName())) {
            return Optional.of(new CoreError("Team name", "Team with this property not found."));
        } else {
            return Optional.empty();
        }
    }

}
