package s1vskcsgobet.core.validators.bet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import s1vskcsgobet.core.database.BetRepository;
import s1vskcsgobet.core.requests.bet.DeleteBetByIdRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteBetByIdRequestValidatorTest {

    @Mock
    private BetRepository betRepository;
    @InjectMocks
    private DeleteBetByIdRequestValidator validator;

    private DeleteBetByIdRequest request;

    @BeforeEach
    public void setup() {
        request = new DeleteBetByIdRequest(10L);
    }

    @Test
    public void shouldReturnError_whenIdIsNull() {
        request.setBetId(null);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Bet ID", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenIdNotFound() {
        Mockito.when(betRepository.existsById(10L)).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Bet ID", errors.get(0).getField());
        assertEquals("Bet with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors() {
        Mockito.when(betRepository.existsById(10L)).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

}