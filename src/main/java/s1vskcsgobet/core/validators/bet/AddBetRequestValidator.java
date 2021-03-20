package s1vskcsgobet.core.validators.bet;

import org.springframework.stereotype.Component;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.requests.bet.AddBetRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AddBetRequestValidator {

    private final TeamRepository teamRepository;

    public AddBetRequestValidator(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<CoreError> validate(AddBetRequest request) {
        List<CoreError> errors = new ArrayList<>();
        validateTeamName(request.getTeamAName()).ifPresent(errors::add);
        validateTeamName(request.getTeamBName()).ifPresent(errors::add);
        validateTeamNamesForEquality(request.getTeamAName(), request.getTeamBName()).ifPresent(errors::add);
        validateCoefficient(request.getCoefficientTeamA()).ifPresent(errors::add);
        validateCoefficient(request.getCoefficientTeamB()).ifPresent(errors::add);
        validateStage(request.getStage()).ifPresent(errors::add);
        return errors;
    }

    private Optional<CoreError> validateTeamName(String teamName) {
        if (teamName == null || teamName.isBlank()) {
            return Optional.of(new CoreError("Team name", "must not be empty!"));
        } else if (!teamRepository.existsByNameIgnoreCase(teamName)) {
            return Optional.of(new CoreError("Team name", "Team with this property not found."));
        } else {
            return Optional.empty();
        }
    }

    private Optional<CoreError> validateTeamNamesForEquality(String teamAName, String teamBName) {
        if (teamAName.equalsIgnoreCase(teamBName)) {
            return Optional.of(new CoreError("Team names", "must not be the same!"));
        } else {
            return Optional.empty();
        }
    }

    private Optional<CoreError> validateCoefficient(BigDecimal coefficient) {
        if (coefficient == null || coefficient.equals(BigDecimal.ZERO)) {
            return Optional.of(new CoreError("Coefficient", "must not be empty!"));
        } else if (coefficient.compareTo(BigDecimal.ONE) <= 0) {
            return Optional.of(new CoreError("Coefficient", "must be greater than 1.00"));
        } else {
            return Optional.empty();
        }
    }

    private Optional<CoreError> validateStage(String stage) {
        if (stage == null || stage.isBlank()) {
            return Optional.of(new CoreError("Stage", "must not be empty!"));
        } else {
            return Optional.empty();
        }
    }

}
