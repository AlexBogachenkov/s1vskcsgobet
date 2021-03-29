package s1vskcsgobet.core.validators.bet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import s1vskcsgobet.core.database.BetRepository;
import s1vskcsgobet.core.requests.bet.ChangeBetIsActiveStatusRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ChangeBetIsActiveStatusRequestValidatorTest {

    @Mock
    private BetRepository betRepository;
    @InjectMocks
    private ChangeBetIsActiveStatusRequestValidator validator;

    @Test
    public void shouldReturnError_whenIdIsNull() {
        ChangeBetIsActiveStatusRequest request = new ChangeBetIsActiveStatusRequest(null, true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Bet ID", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenIdNotFound() {
        ChangeBetIsActiveStatusRequest request = new ChangeBetIsActiveStatusRequest(10L, true);
        Mockito.when(betRepository.existsById(10L)).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Bet ID", errors.get(0).getField());
        assertEquals("Bet with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors() {
        ChangeBetIsActiveStatusRequest request = new ChangeBetIsActiveStatusRequest(10L, true);
        Mockito.when(betRepository.existsById(10L)).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

}