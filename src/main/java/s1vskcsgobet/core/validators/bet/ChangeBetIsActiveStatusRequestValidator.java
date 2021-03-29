package s1vskcsgobet.core.validators.bet;


import org.springframework.stereotype.Component;
import s1vskcsgobet.core.database.BetRepository;
import s1vskcsgobet.core.requests.bet.ChangeBetIsActiveStatusRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ChangeBetIsActiveStatusRequestValidator {

    private BetRepository betRepository;

    public ChangeBetIsActiveStatusRequestValidator(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public List<CoreError> validate(ChangeBetIsActiveStatusRequest request) {
        List<CoreError> errors = new ArrayList<>();
        validateId(request.getBetId()).ifPresent(errors::add);
        return errors;
    }

    private Optional<CoreError> validateId(Long id) {
        if (id == null) {
            return Optional.of(new CoreError("Bet ID", "must not be empty!"));
        } else if (!betRepository.existsById(id)) {
            return Optional.of(new CoreError("Bet ID", "Bet with this property not found."));
        } else {
            return Optional.empty();
        }
    }

}
