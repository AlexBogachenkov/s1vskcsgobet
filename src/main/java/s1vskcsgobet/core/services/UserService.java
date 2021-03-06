package s1vskcsgobet.core.services;

import org.springframework.stereotype.Service;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.domain.User;
import s1vskcsgobet.core.requests.user.AddUserRequest;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.user.AddUserResponse;
import s1vskcsgobet.core.validators.user.AddUserRequestValidator;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddUserRequestValidator addUserRequestValidator;

    public UserService(UserRepository userRepository, AddUserRequestValidator addUserRequestValidator) {
        this.userRepository = userRepository;
        this.addUserRequestValidator = addUserRequestValidator;
    }

    public AddUserResponse add(AddUserRequest request) {
        List<CoreError> errors = addUserRequestValidator.validate(request);
        if (!errors.isEmpty()) {
            return new AddUserResponse(errors);
        }
        User user = new User(request.getUserNickname(), request.getUserPassword(), request.getUserBalance(), request.getUserRole());
        User addedUser = userRepository.save(user);
        return new AddUserResponse(addedUser);
    }

}
