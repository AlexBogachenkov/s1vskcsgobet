package s1vskcsgobet.core.validators.user_bet;

import org.springframework.stereotype.Component;
import s1vskcsgobet.core.database.BetRepository;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.requests.user_bet.AddUserBetRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AddUserBetRequestValidator {

    private UserRepository userRepository;
    private BetRepository betRepository;
    private TeamRepository teamRepository;

    public AddUserBetRequestValidator(UserRepository userRepository, BetRepository betRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.betRepository = betRepository;
        this.teamRepository = teamRepository;
    }

    public List<CoreError> validate(AddUserBetRequest request) {
        List<CoreError> errors = new ArrayList<>();
        validateUserId(request.getUserId()).ifPresent(errors::add);
        validateBetId(request.getBetId()).ifPresent(errors::add);
        validateWinningTeamName(request.getWinningTeamName()).ifPresent(errors::add);
        validateWinningTeamCoefficient(request.getWinningTeamCoefficient()).ifPresent(errors::add);
        validateAmount(request.getUserId(), request.getAmount()).ifPresent(errors::add);
        return errors;
    }

    private Optional<CoreError> validateUserId(Long userId) {
        if (userId == null) {
            return Optional.of(new CoreError("User ID", "must not be empty!"));
        } else if (!userRepository.existsById(userId)) {
            return Optional.of(new CoreError("User ID", "User with this property not found."));
        } else {
            return Optional.empty();
        }
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

    private Optional<CoreError> validateWinningTeamName(String winningTeamName) {
        if (winningTeamName == null || winningTeamName.isBlank()) {
            return Optional.of(new CoreError("Winning team name", "must not be empty!"));
        } else if (!teamRepository.existsByNameIgnoreCase(winningTeamName)) {
            return Optional.of(new CoreError("Team name", "Team with this property not found."));
        } else {
            return Optional.empty();
        }
    }

    private Optional<CoreError> validateWinningTeamCoefficient(BigDecimal coefficient) {
        if (coefficient == null) {
            return Optional.of(new CoreError("Winning team coefficient", "must not be empty!"));
        } else if (coefficient.compareTo(new BigDecimal("1.00")) < 0) {
            return Optional.of(new CoreError("Winning team coefficient", "must be equal to or greater than 1.00."));
        } else {
            return Optional.empty();
        }
    }

    private Optional<CoreError> validateAmount(Long userId, BigDecimal amount) {
        if (amount == null) {
            return Optional.of(new CoreError("Bet amount", "must not be empty!"));
        } else if (amount.compareTo(new BigDecimal("1.00")) < 0) {
            return Optional.of(new CoreError("Bet amount", "must be equal to or greater than 1.00 JURCOIN."));
        } else if (amount.compareTo(userRepository.findById(userId).get().getBalance()) > 0) {
            return Optional.of(new CoreError("Bet amount", "must not be greater than balance."));
        } else {
            return Optional.empty();
        }
    }

}
