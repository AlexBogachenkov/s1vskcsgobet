package s1vskcsgobet.core.validators.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.requests.user.DeleteUserByNicknameRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserByNicknameRequestValidatorTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private DeleteUserByNicknameRequestValidator validator;

    @Test
    public void shouldReturnError_whenNicknameIsNull() {
        DeleteUserByNicknameRequest request = new DeleteUserByNicknameRequest(null);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User nickname", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenNicknameIsEmpty() {
        DeleteUserByNicknameRequest request = new DeleteUserByNicknameRequest("");
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User nickname", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenNicknameIsBlank() {
        DeleteUserByNicknameRequest request = new DeleteUserByNicknameRequest("   ");
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User nickname", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenNicknameNotExists() {
        DeleteUserByNicknameRequest request = new DeleteUserByNicknameRequest("Nickname");
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User nickname", errors.get(0).getField());
        assertEquals("User with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors() {
        DeleteUserByNicknameRequest request = new DeleteUserByNicknameRequest("Nickname");
        Mockito.when(userRepository.existsByNicknameIgnoreCase("Nickname")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

}