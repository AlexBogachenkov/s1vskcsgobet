package s1vskcsgobet.core.validators.user;

import org.springframework.stereotype.Component;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.requests.user.DeleteUserByNicknameRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DeleteUserByNicknameValidator {

    private final UserRepository userRepository;

    public DeleteUserByNicknameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<CoreError> validate(DeleteUserByNicknameRequest request) {
        List<CoreError> errors = new ArrayList<>();
        validateNickname(request).ifPresent(errors::add);
        return errors;
    }

    private Optional<CoreError> validateNickname(DeleteUserByNicknameRequest request) {
        if (request.getNickname() == null || request.getNickname().isEmpty() || request.getNickname().isBlank()) {
            return Optional.of(new CoreError("User nickname", "must not be empty!"));
        } else if (!userRepository.existsByNicknameIgnoreCase(request.getNickname())) {
            return Optional.of(new CoreError("User nickname", "User with this property not found."));
        } else {
            return Optional.empty();
        }
    }

}
