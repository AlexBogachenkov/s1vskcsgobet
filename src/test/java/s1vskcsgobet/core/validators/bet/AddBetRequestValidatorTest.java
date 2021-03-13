package s1vskcsgobet.core.validators.bet;

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

    @Test
    public void shouldReturnError_whenTeamANameIsEmpty() {
        AddBetRequest request = new AddBetRequest("", "TeamBName",
                new BigDecimal("2.23"), new BigDecimal("1.45"), true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamANameIsBlank() {
        AddBetRequest request = new AddBetRequest("     ", "TeamBName",
                new BigDecimal("2.23"), new BigDecimal("1.45"), true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamANotFound() {
        AddBetRequest request = new AddBetRequest("TeamAName", "TeamBName",
                new BigDecimal("2.23"), new BigDecimal("1.45"), true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(false);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("Team with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamBNameIsEmpty() {
        AddBetRequest request = new AddBetRequest("TeamAName", "",
                new BigDecimal("2.23"), new BigDecimal("1.45"), true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamBNameIsBlank() {
        AddBetRequest request = new AddBetRequest("TeamAName", "     ",
                new BigDecimal("2.23"), new BigDecimal("1.45"), true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamBNotFound() {
        AddBetRequest request = new AddBetRequest("TeamAName", "TeamBName",
                new BigDecimal("2.23"), new BigDecimal("1.45"), true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("Team with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamNamesAreEqual() {
        AddBetRequest request = new AddBetRequest("TeamAName", "TeamAName",
                new BigDecimal("2.23"), new BigDecimal("1.45"), true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team names", errors.get(0).getField());
        assertEquals("must not be the same!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamAIsNull() {
        AddBetRequest request = new AddBetRequest("TeamAName", "TeamBName",
                null, new BigDecimal("1.45"), true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamAIsEmpty() {
        AddBetRequest request = new AddBetRequest("TeamAName", "TeamBName",
                BigDecimal.ZERO, new BigDecimal("1.45"), true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamAIsLessThanOne() {
        AddBetRequest request = new AddBetRequest("TeamAName", "TeamBName",
                new BigDecimal("0.23"), new BigDecimal("1.45"), true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must be greater than 1.00", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamAEqualsOne() {
        AddBetRequest request = new AddBetRequest("TeamAName", "TeamBName",
                new BigDecimal("1.00"), new BigDecimal("1.45"), true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must be greater than 1.00", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamBIsNull() {
        AddBetRequest request = new AddBetRequest("TeamAName", "TeamBName",
                new BigDecimal("1.45"), null, true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamBIsEmpty() {
        AddBetRequest request = new AddBetRequest("TeamAName", "TeamBName",
                new BigDecimal("1.45"), BigDecimal.ZERO, true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamBIsLessThanOne() {
        AddBetRequest request = new AddBetRequest("TeamAName", "TeamBName",
                new BigDecimal("1.45"), new BigDecimal("0.23"), true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must be greater than 1.00", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamBEqualsOne() {
        AddBetRequest request = new AddBetRequest("TeamAName", "TeamBName",
                new BigDecimal("1.45"), new BigDecimal("1.00"), true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must be greater than 1.00", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors() {
        AddBetRequest request = new AddBetRequest("TeamAName", "TeamBName",
                new BigDecimal("1.45"), new BigDecimal("1.10"), true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

}