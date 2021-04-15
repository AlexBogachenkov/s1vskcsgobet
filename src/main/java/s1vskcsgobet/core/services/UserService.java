package s1vskcsgobet.core.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.domain.Role;
import s1vskcsgobet.core.domain.User;
import s1vskcsgobet.core.requests.user.*;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.user.AddUserResponse;
import s1vskcsgobet.core.responses.user.DeleteUserByNicknameResponse;
import s1vskcsgobet.core.responses.user.FindAllUsersResponse;
import s1vskcsgobet.core.responses.user.SignupUserResponse;
import s1vskcsgobet.core.validators.user.AddUserRequestValidator;
import s1vskcsgobet.core.validators.user.DeleteUserByNicknameRequestValidator;
import s1vskcsgobet.core.validators.user.SignupUserRequestValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddUserRequestValidator addUserRequestValidator;
    private final SignupUserRequestValidator signupUserRequestValidator;
    private final DeleteUserByNicknameRequestValidator deleteUserByNicknameRequestValidator;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, AddUserRequestValidator addUserRequestValidator,
                       SignupUserRequestValidator signupUserRequestValidator,
                       DeleteUserByNicknameRequestValidator deleteUserByNicknameRequestValidator, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.addUserRequestValidator = addUserRequestValidator;
        this.signupUserRequestValidator = signupUserRequestValidator;
        this.deleteUserByNicknameRequestValidator = deleteUserByNicknameRequestValidator;
        this.encoder = encoder;
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

    public SignupUserResponse signup(SignupUserRequest request) {
        List<CoreError> errors = signupUserRequestValidator.validate(request);
        if (!errors.isEmpty()) {
            return new SignupUserResponse(errors);
        }
        User user = new User();
        user.setNickname(request.getNickname());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setBalance(new BigDecimal("1000.00"));
        user.setRole(Role.USER);
        userRepository.save(user);
        return new SignupUserResponse(true);
    }

    @Transactional
    public DeleteUserByNicknameResponse deleteByNickname(DeleteUserByNicknameRequest request) {
        List<CoreError> errors = deleteUserByNicknameRequestValidator.validate(request);
        if (!errors.isEmpty()) {
            return new DeleteUserByNicknameResponse(errors);
        }
        int deletedUserCount = userRepository.deleteByNicknameIgnoreCase(request.getNickname());
        return new DeleteUserByNicknameResponse(deletedUserCount > 0);
    }

    @Transactional
    public void topUpBalance(TopUpUserBalanceRequest request){
        User user = userRepository.findById(request.getUserId()).get();
        user.setBalance(user.getBalance().add(request.getAmount()));
        userRepository.save(user);
    }

    @Transactional
    public void withdrawFromBalance(WithdrawFromUserBalanceRequest request){
        User user = userRepository.findById(request.getUserId()).get();
        user.setBalance(user.getBalance().subtract(request.getAmount()));
        userRepository.save(user);
    }

    public Optional<User> findUser(String nickname) {
        return userRepository.findByNicknameIgnoreCase(nickname);
    }

    public FindAllUsersResponse findAll() {
        List<User> allUsers = userRepository.findAll();
        return new FindAllUsersResponse(allUsers);
    }

}