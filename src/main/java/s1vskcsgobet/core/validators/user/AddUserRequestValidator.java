package s1vskcsgobet.core.validators.user;

import org.springframework.stereotype.Component;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.domain.Role;
import s1vskcsgobet.core.requests.user.AddUserRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AddUserRequestValidator {

    private final UserRepository userRepository;

    public AddUserRequestValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<CoreError> validate(AddUserRequest request) {
        List<CoreError> errors = new ArrayList<>();
        validateNickname(request).ifPresent(errors::add);
        validatePassword(request).ifPresent(errors::add);
        validateBalance(request).ifPresent(errors::add);
        validateRole(request).ifPresent(errors::add);
        return errors;
    }

    private Optional<CoreError> validateNickname(AddUserRequest request) {
        if (request.getUserNickname() == null || request.getUserNickname().isBlank()) {
            return Optional.of(new CoreError("User nickname", "must not be empty!"));
        } else if (userRepository.existsByNicknameIgnoreCase(request.getUserNickname())) {
            return Optional.of(new CoreError("User nickname", "User with this property already exists."));
        } else {
            return Optional.empty();
        }
    }

    private Optional<CoreError> validatePassword(AddUserRequest request) {
        if (request.getUserPassword() == null || request.getUserPassword().isBlank()) {
            return Optional.of(new CoreError("User password", "must not be empty and must not contain only spaces!"));
        } else if (request.getUserPassword().length() < 6 || request.getUserPassword().length() > 20) {
            return Optional.of(new CoreError("User password", "must contain from 6 to 20 characters(including)."));
        } else {
            return Optional.empty();
        }
    }

    private Optional<CoreError> validateBalance(AddUserRequest request) {
        if (request.getUserBalance() == null) {
            return Optional.of(new CoreError("User balance", "must not be empty!"));
        } else if (request.getUserBalance().compareTo(BigDecimal.ZERO) < 0) {
            return Optional.of(new CoreError("User balance", "must not be less than 0.00."));
        } else {
            return Optional.empty();
        }
    }

    private Optional<CoreError> validateRole(AddUserRequest request) {
        if (request.getUserRole() == null || request.getUserRole().isBlank()) {
            return Optional.of(new CoreError("User role", "must not be empty!"));
        } else if (request.getUserRole() != Role.USER && request.getUserRole() != Role.MODERATOR &&
                request.getUserRole() != Role.ADMIN) {
            return Optional.of(new CoreError("User role", "must be 'USER' or 'MODERATOR' or 'ADMIN'."));
        } else {
            return Optional.empty();
        }
    }

}
