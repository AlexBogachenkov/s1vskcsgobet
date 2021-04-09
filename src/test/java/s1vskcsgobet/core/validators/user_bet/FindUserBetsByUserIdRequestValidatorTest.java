package s1vskcsgobet.core.validators.user_bet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.requests.user_bet.FindUserBetsByUserIdRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FindUserBetsByUserIdRequestValidatorTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private FindUserBetsByUserIdRequestValidator validator;

    private FindUserBetsByUserIdRequest request;

    @BeforeEach
    public void setup() {
        request = new FindUserBetsByUserIdRequest(2L);
    }

    @Test
    public void shouldReturnErrorList_whenIdIsNull() {
        request.setUserId(null);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User ID", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorList_whenIdNotFound() {
        Mockito.when(userRepository.existsById(2L)).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User ID", errors.get(0).getField());
        assertEquals("User with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors() {
        Mockito.when(userRepository.existsById(2L)).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

}