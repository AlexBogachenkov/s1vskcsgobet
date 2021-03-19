package s1vskcsgobet.core.services;

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
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.user.AddUserResponse;
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

    @Test
    public void shouldReturnErrorList_whenAddUserRequestValidationNotPassed() {
        AddUserRequest request = new AddUserRequest("", "password",
                new BigDecimal("100"), Role.USER);
        List<CoreError> errors = new ArrayList<>();
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

}