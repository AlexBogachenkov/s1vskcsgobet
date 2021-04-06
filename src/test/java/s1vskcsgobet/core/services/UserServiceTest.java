package s1vskcsgobet.core.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.domain.Role;
import s1vskcsgobet.core.domain.User;
import s1vskcsgobet.core.requests.user.AddUserRequest;
import s1vskcsgobet.core.requests.user.DeleteUserByNicknameRequest;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.user.AddUserResponse;
import s1vskcsgobet.core.responses.user.DeleteUserByNicknameResponse;
import s1vskcsgobet.core.responses.user.FindAllUsersResponse;
import s1vskcsgobet.core.validators.user.AddUserRequestValidator;
import s1vskcsgobet.core.validators.user.DeleteUserByNicknameRequestValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AddUserRequestValidator addUserRequestValidator;
    @Mock
    private DeleteUserByNicknameRequestValidator deleteUserByNicknameRequestValidator;
    @InjectMocks
    private UserService userService;

    private List<CoreError> errors;

    @BeforeEach
    public void setup() {
        errors = new ArrayList<>();
    }

    @Test
    public void shouldReturnErrorList_whenAddUserRequestValidationNotPassed() {
        AddUserRequest request = new AddUserRequest("", "password",
                new BigDecimal("100"), Role.USER);
        errors.add(new CoreError("User nickname", "must not be empty!"));
        Mockito.when(addUserRequestValidator.validate(request)).thenReturn(errors);
        AddUserResponse response = userService.add(request);

        assertTrue(response.hasErrors());
        assertEquals(1, response.getErrors().size());
        assertEquals("User nickname", response.getErrors().get(0).getField());
        assertEquals("must not be empty!", response.getErrors().get(0).getMessage());
    }

    @Test
    public void shouldReturnAddedUser() {
        User user = new User("nickname", "password",
                new BigDecimal("100"), Role.USER);
        AddUserRequest request = new AddUserRequest(user.getNickname(), user.getPassword(),
                user.getBalance(), user.getRole());
        Mockito.when(addUserRequestValidator.validate(request)).thenReturn(new ArrayList<>());
        Mockito.when(userRepository.save(user)).thenReturn(user);
        AddUserResponse response = userService.add(request);

        assertFalse(response.hasErrors());
        assertNotNull(response.getAddedUser());
        assertEquals("nickname", response.getAddedUser().getNickname());
    }

    @Test
    public void shouldReturnErrorList_whenDeleteUserByNicknameRequestValidationNotPassed() {
        DeleteUserByNicknameRequest request = new DeleteUserByNicknameRequest("");
        errors.add(new CoreError("User nickname", "must not be empty!"));
        Mockito.when(deleteUserByNicknameRequestValidator.validate(request)).thenReturn(errors);
        DeleteUserByNicknameResponse response = userService.deleteByNickname(request);

        assertTrue(response.hasErrors());
        assertEquals(1, response.getErrors().size());
        assertEquals("User nickname", response.getErrors().get(0).getField());
        assertEquals("must not be empty!", response.getErrors().get(0).getMessage());
    }

    @Test
    public void shouldReturnIsDeleted() {
        DeleteUserByNicknameRequest request = new DeleteUserByNicknameRequest("nickname");
        Mockito.when(deleteUserByNicknameRequestValidator.validate(request)).thenReturn(new ArrayList<>());
        Mockito.when(userRepository.deleteByNicknameIgnoreCase(request.getNickname())).thenReturn(1);
        DeleteUserByNicknameResponse response = userService.deleteByNickname(request);

        assertFalse(response.hasErrors());
        assertTrue(response.isDeleted());
    }

    @Test
    public void shouldReturnAllUsers() {
        User userA = new User("nicknameA", "passwordA",
                new BigDecimal("100"), Role.USER);
        User userB = new User("nicknameB", "passwordB",
                new BigDecimal("100"), Role.USER);
        List<User> users = List.of(userA, userB);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        FindAllUsersResponse response = userService.findAll();

        assertFalse(response.hasErrors());
        assertEquals("nicknameA", response.getAllUsers().get(0).getNickname());
        assertEquals("nicknameB", response.getAllUsers().get(1).getNickname());
    }

}