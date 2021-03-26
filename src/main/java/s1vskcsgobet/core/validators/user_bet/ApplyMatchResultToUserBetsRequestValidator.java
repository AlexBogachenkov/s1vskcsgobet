package s1vskcsgobet.core.validators.user_bet;

import org.springframework.stereotype.Component;
import s1vskcsgobet.core.database.BetRepository;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.requests.user_bet.ApplyMatchResultToUserBetsRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ApplyMatchResultToUserBetsRequestValidator {

    private BetRepository betRepository;
    private TeamRepository teamRepository;

    public ApplyMatchResultToUserBetsRequestValidator(BetRepository betRepository, TeamRepository teamRepository) {
        this.betRepository = betRepository;
        this.teamRepository = teamRepository;
    }

    public List<CoreError> validate(ApplyMatchResultToUserBetsRequest request) {
        List<CoreError> errors = new ArrayList<>();
        validateBetId(request.getBetId()).ifPresent(errors::add);
        validateWinningTeamName(request).ifPresent(errors::add);
        return errors;
    }

    private Optional<CoreError> validateBetId(Long betId) {
        if (betId == null) {
            return Optional.of(new CoreError("Bet ID", "must not be empty!"));
        } else if (!betRepository.existsById(betId)) {
            return Optional.of(new CoreError("Bet ID", "Bet with this property not found."));
        } else {
            return Optional.empty();
        }
    }

    private Optional<CoreError> validateWinningTeamName(ApplyMatchResultToUserBetsRequest request) {
        if (request.getWinningTeamName() == null || request.getWinningTeamName().isBlank()) {
            return Optional.of(new CoreError("Winning team name", "must not be empty!"));
        } else if (!teamRepository.existsByNameIgnoreCase(request.getWinningTeamName())) {
            return Optional.of(new CoreError("Winning team name", "Team with this property not found."));
        } else if (!betRepository.existsByIdAndTeamName(request.getBetId(), request.getWinningTeamName())) {
            return Optional.of(new CoreError("Winning team name", "Bet with this property not found."));
        } else {
            return Optional.empty();
        }
    }

}
