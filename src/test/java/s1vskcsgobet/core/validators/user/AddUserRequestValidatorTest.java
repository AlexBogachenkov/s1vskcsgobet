package s1vskcsgobet.core.validators.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.domain.Role;
import s1vskcsgobet.core.requests.user.AddUserRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddUserRequestValidatorTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AddUserRequestValidator validator;

    private AddUserRequest request;

    @BeforeEach
    public void setup() {
        request = new AddUserRequest("Nickname", "password",
                new BigDecimal("1000"), Role.USER);
    }

    @Test
    public void shouldReturnError_whenNicknameIsNull() {
        request.setUserNickname(null);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User nickname", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenNicknameIsEmpty() {
        request.setUserNickname("");
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User nickname", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenNicknameIsBlank() {
        request.setUserNickname("   ");
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User nickname", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenNicknameAlreadyExists() {
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User nickname", errors.get(0).getField());
        assertEquals("User with this property already exists.", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenPasswordIsNull() {
        request.setUserPassword(null);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User password", errors.get(0).getField());
        assertEquals("must not be empty and must not contain only spaces!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenPasswordIsEmpty() {
        request.setUserPassword("");
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User password", errors.get(0).getField());
        assertEquals("must not be empty and must not contain only spaces!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenPasswordIsBlank() {
        request.setUserPassword("   ");
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User password", errors.get(0).getField());
        assertEquals("must not be empty and must not contain only spaces!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenPasswordLengthIsLessThan6() {
        request.setUserPassword("12345");
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User password", errors.get(0).getField());
        assertEquals("must contain from 6 to 20 characters(including).", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors_whenPasswordLengthEquals6() {
        request.setUserPassword("123456");
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldNotReturnErrors_whenPasswordLengthIsBetween6And20() {
        request.setUserPassword("123456789");
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldNotReturnErrors_whenPasswordLengthEquals20() {
        request.setUserPassword("12345123451234512345");
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldReturnError_whenPasswordLengthIsMoreThan20() {
        request.setUserPassword("123451234512345123451");
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User password", errors.get(0).getField());
        assertEquals("must contain from 6 to 20 characters(including).", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenBalanceIsNull() {
        request.setUserBalance(null);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User balance", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenBalanceIsLessThanZero() {
        request.setUserBalance(new BigDecimal("-20"));
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User balance", errors.get(0).getField());
        assertEquals("must not be less than 0.00.", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors_whenBalanceEqualsZero() {
        request.setUserBalance(new BigDecimal("0"));
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldReturnError_whenRoleIsNull() {
        request.setUserRole(null);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User role", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors_whenRoleIsAllowedValueUser() {
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldNotReturnErrors_whenRoleIsAllowedValueModerator() {
        request.setUserRole(Role.MODERATOR);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldNotReturnErrors_whenRoleIsAllowedValueAdmin() {
        request.setUserRole(Role.ADMIN);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldNotReturnErrors() {
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

}