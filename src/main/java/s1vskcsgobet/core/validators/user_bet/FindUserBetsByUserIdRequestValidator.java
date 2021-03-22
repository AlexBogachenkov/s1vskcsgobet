package s1vskcsgobet.core.validators.user_bet;

import org.springframework.stereotype.Component;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.requests.user_bet.FindUserBetsByUserIdRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FindUserBetsByUserIdRequestValidator {

    private UserRepository userRepository;

    public FindUserBetsByUserIdRequestValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<CoreError> validate(FindUserBetsByUserIdRequest request) {
        List<CoreError> errors = new ArrayList<>();
        validateUserId(request.getUserId()).ifPresent(errors::add);
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

}
