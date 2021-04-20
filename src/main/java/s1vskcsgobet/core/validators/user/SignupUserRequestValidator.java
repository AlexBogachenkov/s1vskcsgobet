package s1vskcsgobet.core.validators.user;

import org.springframework.stereotype.Component;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.requests.user.SignupUserRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SignupUserRequestValidator {

    private final UserRepository userRepository;

    public SignupUserRequestValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<CoreError> validate(SignupUserRequest request) {
        List<CoreError> errors = new ArrayList<>();
        validateNickname(request.getNickname()).ifPresent(errors::add);
        validatePassword(request.getPassword()).ifPresent(errors::add);
        validatePasswordsForEquality(request.getPassword(), request.getPasswordAgain()).ifPresent(errors::add);
        return errors;
    }

    private Optional<CoreError> validateNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            return Optional.of(new CoreError("Nickname", "must not be empty!"));
        } else if (!nickname.matches("[a-zA-Z0-9]*")) {
            return Optional.of(new CoreError("Nickname", "must contain only letters or numbers."));
        } else if (nickname.length() < 4 || nickname.length() > 20) {
            return Optional.of(new CoreError("Nickname", "must contain from 4 to 20 characters(including)."));
        } else if (userRepository.existsByNicknameIgnoreCase(nickname)) {
            return Optional.of(new CoreError("Nickname", "User with this property already exists."));
        } else {
            return Optional.empty();
        }
    }

    private Optional<CoreError> validatePassword(String password) {
        if (password == null || password.isBlank()) {
            return Optional.of(new CoreError("Password", "must not be empty and must not contain spaces!"));
        } else if (password.contains(" ")) {
            return Optional.of(new CoreError("Password", "must not contain spaces."));
        } else if (password.length() < 6 || password.length() > 20) {
            return Optional.of(new CoreError("Password", "must contain from 6 to 20 characters(including)."));
        } else {
            return Optional.empty();
        }
    }

    private Optional<CoreError> validatePasswordsForEquality(String password, String passwordAgain) {
        if (!password.equals(passwordAgain)) {
            return Optional.of(new CoreError("Passwords", "are not equal!"));
        } else {
            return Optional.empty();
        }
    }

}
