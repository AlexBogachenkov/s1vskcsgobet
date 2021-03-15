package s1vskcsgobet.core.validators.user;

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

    @Test
    public void shouldReturnError_whenNicknameIsNull() {
        AddUserRequest request = new AddUserRequest(null, "password",
                new BigDecimal("1000"), Role.USER);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User nickname", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenNicknameIsEmpty() {
        AddUserRequest request = new AddUserRequest("", "password",
                new BigDecimal("1000"), Role.USER);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User nickname", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenNicknameIsBlank() {
        AddUserRequest request = new AddUserRequest("   ", "password",
                new BigDecimal("1000"), Role.USER);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User nickname", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenNicknameAlreadyExists() {
        AddUserRequest request = new AddUserRequest("Nickname", "password",
                new BigDecimal("1000"), Role.USER);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User nickname", errors.get(0).getField());
        assertEquals("User with this property already exists.", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenPasswordIsNull() {
        AddUserRequest request = new AddUserRequest("Nickname", null,
                new BigDecimal("1000"), Role.USER);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User password", errors.get(0).getField());
        assertEquals("must not be empty and must not contain only spaces!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenPasswordIsEmpty() {
        AddUserRequest request = new AddUserRequest("Nickname", "",
                new BigDecimal("1000"), Role.USER);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User password", errors.get(0).getField());
        assertEquals("must not be empty and must not contain only spaces!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenPasswordIsBlank() {
        AddUserRequest request = new AddUserRequest("Nickname", "   ",
                new BigDecimal("1000"), Role.USER);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User password", errors.get(0).getField());
        assertEquals("must not be empty and must not contain only spaces!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenPasswordLengthIsLessThan6() {
        AddUserRequest request = new AddUserRequest("Nickname", "12345",
                new BigDecimal("1000"), Role.USER);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User password", errors.get(0).getField());
        assertEquals("must contain from 6 to 20 characters(including).", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors_whenPasswordLengthEquals6() {
        AddUserRequest request = new AddUserRequest("Nickname", "123456",
                new BigDecimal("1000"), Role.USER);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldNotReturnErrors_whenPasswordLengthIsBetween6And20() {
        AddUserRequest request = new AddUserRequest("Nickname", "123456789",
                new BigDecimal("1000"), Role.USER);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldNotReturnErrors_whenPasswordLengthEquals20() {
        AddUserRequest request = new AddUserRequest("Nickname", "12345123451234512345",
                new BigDecimal("1000"), Role.USER);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldReturnError_whenPasswordLengthIsMoreThan20() {
        AddUserRequest request = new AddUserRequest("Nickname", "123451234512345123451",
                new BigDecimal("1000"), Role.USER);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User password", errors.get(0).getField());
        assertEquals("must contain from 6 to 20 characters(including).", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenBalanceIsNull() {
        AddUserRequest request = new AddUserRequest("Nickname", "password",
                null, Role.USER);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User balance", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenBalanceIsLessThanZero() {
        AddUserRequest request = new AddUserRequest("Nickname", "password",
                new BigDecimal("-20"), Role.USER);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User balance", errors.get(0).getField());
        assertEquals("must not be less than 0.00.", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors_whenBalanceEqualsZero() {
        AddUserRequest request = new AddUserRequest("Nickname", "password",
                new BigDecimal("0"), Role.USER);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldReturnError_whenRoleIsNull() {
        AddUserRequest request = new AddUserRequest("Nickname", "password",
                new BigDecimal("100"), null);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User role", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenRoleIsAllowedValueUser() {
        AddUserRequest request = new AddUserRequest("Nickname", "password",
                new BigDecimal("100"), Role.USER);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldReturnError_whenRoleIsAllowedValueModerator() {
        AddUserRequest request = new AddUserRequest("Nickname", "password",
                new BigDecimal("100"), Role.MODERATOR);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldReturnError_whenRoleIsAllowedValueAdmin() {
        AddUserRequest request = new AddUserRequest("Nickname", "password",
                new BigDecimal("100"), Role.ADMIN);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldNotReturnErrors() {
        AddUserRequest request = new AddUserRequest("nickname", "password",
                new BigDecimal("1000"), Role.USER);
        Mockito.when(userRepository.existsByNicknameIgnoreCase("nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

}