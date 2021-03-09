package s1vskcsgobet.core.validators.bet;

import org.springframework.stereotype.Component;
import s1vskcsgobet.core.database.BetRepository;
import s1vskcsgobet.core.requests.bet.DeleteBetByIdRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DeleteBetByIdRequestValidator {

    private final BetRepository betRepository;

    public DeleteBetByIdRequestValidator(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public List<CoreError> validate(DeleteBetByIdRequest request) {
        List<CoreError> errors = new ArrayList<>();
        validateId(request).ifPresent(errors::add);
        return errors;
    }

    private Optional<CoreError> validateId(DeleteBetByIdRequest request) {
        if (request.getBetId() == null) {
            return Optional.of(new CoreError("Bet ID", "must not be empty!"));
        } else if (!betRepository.existsById(request.getBetId())) {
            return Optional.of(new CoreError("Bet ID", "Bet with this property not found."));
        } else {
            return Optional.empty();
        }
    }

}
