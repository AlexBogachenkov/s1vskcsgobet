package s1vskcsgobet.core.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.domain.User;
import s1vskcsgobet.core.requests.user.AddUserRequest;
import s1vskcsgobet.core.requests.user.DeleteUserByNicknameRequest;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.team.FindAllTeamsResponse;
import s1vskcsgobet.core.responses.user.AddUserResponse;
import s1vskcsgobet.core.responses.user.DeleteUserByNicknameResponse;
import s1vskcsgobet.core.responses.user.FindAllUsersResponse;
import s1vskcsgobet.core.validators.user.AddUserRequestValidator;
import s1vskcsgobet.core.validators.user.DeleteUserByNicknameValidator;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddUserRequestValidator addUserRequestValidator;
    private final DeleteUserByNicknameValidator deleteUserByNicknameValidator;

    public UserService(UserRepository userRepository, AddUserRequestValidator addUserRequestValidator, DeleteUserByNicknameValidator deleteUserByNicknameValidator) {
        this.userRepository = userRepository;
        this.addUserRequestValidator = addUserRequestValidator;
        this.deleteUserByNicknameValidator = deleteUserByNicknameValidator;
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

    @Transactional
    public DeleteUserByNicknameResponse deleteByNickname(DeleteUserByNicknameRequest request) {
        List<CoreError> errors = deleteUserByNicknameValidator.validate(request);
        if (!errors.isEmpty()) {
            return new DeleteUserByNicknameResponse(errors);
        }
        int deletedUserCount = userRepository.deleteByNicknameIgnoreCase(request.getNickname());
        return new DeleteUserByNicknameResponse(deletedUserCount > 0);
    }

    public FindAllUsersResponse findAll() {
        List<User> allUsers = userRepository.findAll();
        return new FindAllUsersResponse(allUsers);
    }

}