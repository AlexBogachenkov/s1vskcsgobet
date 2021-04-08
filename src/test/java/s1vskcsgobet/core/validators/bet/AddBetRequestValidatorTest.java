package s1vskcsgobet.core.validators.bet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.requests.bet.AddBetRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AddBetRequestValidatorTest {

    @Mock
    private TeamRepository teamRepository;
    @InjectMocks
    private AddBetRequestValidator validator;

    private AddBetRequest addBetRequest;

    @BeforeEach
    public void setup() {
        addBetRequest = new AddBetRequest("TeamAName", "TeamBName",
                new BigDecimal("2.23"), new BigDecimal("1.45"), "Final", true);
    }

    @Test
    public void shouldReturnError_whenTeamANameIsEmpty() {
        addBetRequest.setTeamAName("");
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamANameIsBlank() {
        addBetRequest.setTeamAName("   ");
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamANotFound() {
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(false);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("Team with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamBNameIsEmpty() {
        addBetRequest.setTeamBName("");
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamBNameIsBlank() {
        addBetRequest.setTeamBName("   ");
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamBNotFound() {
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(false);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("Team with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamNamesAreEqual() {
        addBetRequest.setTeamBName(addBetRequest.getTeamAName());
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Team names", errors.get(0).getField());
        assertEquals("must not be the same!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamAIsNull() {
        addBetRequest.setCoefficientTeamA(null);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamAIsEmpty() {
        addBetRequest.setCoefficientTeamA(BigDecimal.ZERO);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamAIsLessThanOne() {
        addBetRequest.setCoefficientTeamA(new BigDecimal("0.23"));
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must be greater than 1.00", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamAEqualsOne() {
        addBetRequest.setCoefficientTeamA(new BigDecimal("1.00"));
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must be greater than 1.00", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamBIsNull() {
        addBetRequest.setCoefficientTeamB(null);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamBIsEmpty() {
        addBetRequest.setCoefficientTeamB(BigDecimal.ZERO);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamBIsLessThanOne() {
        addBetRequest.setCoefficientTeamB(new BigDecimal("0.23"));
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must be greater than 1.00", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamBEqualsOne() {
        addBetRequest.setCoefficientTeamB(new BigDecimal("1.00"));
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must be greater than 1.00", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenStageIsNull() {
        addBetRequest.setStage(null);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Stage", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenStageIsEmpty() {
        addBetRequest.setStage("");
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Stage", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenStageIsBlank() {
        addBetRequest.setStage("   ");
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(1, errors.size());
        assertEquals("Stage", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors() {
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(addBetRequest);

        assertEquals(0, errors.size());
    }

}