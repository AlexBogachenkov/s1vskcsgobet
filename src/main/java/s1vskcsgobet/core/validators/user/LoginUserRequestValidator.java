package s1vskcsgobet.core.validators.user;

import org.springframework.stereotype.Component;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.requests.user.LoginUserRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class LoginUserRequestValidator {

    private final UserRepository userRepository;

    public LoginUserRequestValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<CoreError> validate(LoginUserRequest request) {
        List<CoreError> errors = new ArrayList<>();
        validateNickname(request.getNickname()).ifPresent(errors::add);
        validatePassword(request.getPassword()).ifPresent(errors::add);
        validateUserForExisting(request).ifPresent(errors::add);
        return errors;
    }

    private Optional<CoreError> validateNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            return Optional.of(new CoreError("Nickname", "must not be empty!"));
        } else {
            return Optional.empty();
        }
    }

    private Optional<CoreError> validatePassword(String password) {
        if (password == null || password.isBlank()) {
            return Optional.of(new CoreError("Password", "must not be empty!"));
        } else {
            return Optional.empty();
        }
    }

    private Optional<CoreError> validateUserForExisting(LoginUserRequest request) {
        if (!userRepository.existsByNicknameIgnoreCaseAndPassword(request.getNickname(), request.getPassword())) {
            return Optional.of(new CoreError("Nickname or password", "is incorrect!"));
        } else {
            return Optional.empty();
        }
    }

}
