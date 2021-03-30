package s1vskcsgobet.core.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.domain.Role;
import s1vskcsgobet.core.domain.User;
import s1vskcsgobet.core.requests.user.*;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.user.*;
import s1vskcsgobet.core.validators.user.AddUserRequestValidator;
import s1vskcsgobet.core.validators.user.DeleteUserByNicknameRequestValidator;
import s1vskcsgobet.core.validators.user.LoginUserRequestValidator;
import s1vskcsgobet.core.validators.user.SignupUserRequestValidator;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddUserRequestValidator addUserRequestValidator;
    private final SignupUserRequestValidator signupUserRequestValidator;
    private final LoginUserRequestValidator loginUserRequestValidator;
    private final DeleteUserByNicknameRequestValidator deleteUserByNicknameRequestValidator;

    public UserService(UserRepository userRepository, AddUserRequestValidator addUserRequestValidator,
                       SignupUserRequestValidator signupUserRequestValidator,
                       LoginUserRequestValidator loginUserRequestValidator,
                       DeleteUserByNicknameRequestValidator deleteUserByNicknameRequestValidator) {
        this.userRepository = userRepository;
        this.addUserRequestValidator = addUserRequestValidator;
        this.signupUserRequestValidator = signupUserRequestValidator;
        this.loginUserRequestValidator = loginUserRequestValidator;
        this.deleteUserByNicknameRequestValidator = deleteUserByNicknameRequestValidator;
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
        user.setPassword(request.getPassword());
        user.setBalance(new BigDecimal("1000.00"));
        user.setRole(Role.USER);
        userRepository.save(user);
        return new SignupUserResponse(true);
    }

    public LoginUserResponse login(LoginUserRequest request) {
        List<CoreError> errors = loginUserRequestValidator.validate(request);
        if (!errors.isEmpty()) {
            return new LoginUserResponse(errors);
        }
        return new LoginUserResponse(true);
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

    public FindAllUsersResponse findAll() {
        List<User> allUsers = userRepository.findAll();
        return new FindAllUsersResponse(allUsers);
    }

}