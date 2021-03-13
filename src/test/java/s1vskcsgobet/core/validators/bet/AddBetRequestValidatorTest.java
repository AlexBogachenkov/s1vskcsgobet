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
        AddBetRequest request = AddBetRequest.builder()
                .teamAName("")
                .teamBName("TeamBName")
                .coefficientTeamA(new BigDecimal("2.23"))
                .coefficientTeamB(new BigDecimal("1.45"))
                .isActive(true)
                .build();
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamANameIsBlank() {
        AddBetRequest request = AddBetRequest.builder()
                .teamAName("     ")
                .teamBName("TeamBName")
                .coefficientTeamA(new BigDecimal("2.23"))
                .coefficientTeamB(new BigDecimal("1.45"))
                .isActive(true)
                .build();
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamANotFound() {
        AddBetRequest request = AddBetRequest.builder()
                .teamAName("TeamAName")
                .teamBName("TeamBName")
                .coefficientTeamA(new BigDecimal("2.23"))
                .coefficientTeamB(new BigDecimal("1.45"))
                .isActive(true)
                .build();
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(false);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("Team with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamBNameIsEmpty() {
        AddBetRequest request = AddBetRequest.builder()
                .teamAName("TeamAName")
                .teamBName("")
                .coefficientTeamA(new BigDecimal("2.23"))
                .coefficientTeamB(new BigDecimal("1.45"))
                .isActive(true)
                .build();
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamBNameIsBlank() {
        AddBetRequest request = AddBetRequest.builder()
                .teamAName("TeamAName")
                .teamBName("     ")
                .coefficientTeamA(new BigDecimal("2.23"))
                .coefficientTeamB(new BigDecimal("1.45"))
                .isActive(true)
                .build();
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamBNotFound() {
        AddBetRequest request = AddBetRequest.builder()
                .teamAName("TeamAName")
                .teamBName("TeamBName")
                .coefficientTeamA(new BigDecimal("2.23"))
                .coefficientTeamB(new BigDecimal("1.45"))
                .isActive(true)
                .build();
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("Team with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenTeamNamesAreEqual() {
        AddBetRequest request = AddBetRequest.builder()
                .teamAName("TeamAName")
                .teamBName("TeamAName")
                .coefficientTeamA(new BigDecimal("2.23"))
                .coefficientTeamB(new BigDecimal("1.45"))
                .isActive(true)
                .build();
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team names", errors.get(0).getField());
        assertEquals("must not be the same!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamAIsNull() {
        AddBetRequest request = AddBetRequest.builder()
                .teamAName("TeamAName")
                .teamBName("TeamBName")
                .coefficientTeamA(null)
                .coefficientTeamB(new BigDecimal("1.45"))
                .isActive(true)
                .build();
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamAIsEmpty() {
        AddBetRequest request = AddBetRequest.builder()
                .teamAName("TeamAName")
                .teamBName("TeamBName")
                .coefficientTeamA(BigDecimal.ZERO)
                .coefficientTeamB(new BigDecimal("1.45"))
                .isActive(true)
                .build();
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamAIsLessThanOne() {
        AddBetRequest request = AddBetRequest.builder()
                .teamAName("TeamAName")
                .teamBName("TeamBName")
                .coefficientTeamA(new BigDecimal("0.23"))
                .coefficientTeamB(new BigDecimal("1.45"))
                .isActive(true)
                .build();
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must be greater than 1.00", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamAEqualsOne() {
        AddBetRequest request = AddBetRequest.builder()
                .teamAName("TeamAName")
                .teamBName("TeamBName")
                .coefficientTeamA(new BigDecimal("1.00"))
                .coefficientTeamB(new BigDecimal("1.45"))
                .isActive(true)
                .build();
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must be greater than 1.00", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamBIsNull() {
        AddBetRequest request = AddBetRequest.builder()
                .teamAName("TeamAName")
                .teamBName("TeamBName")
                .coefficientTeamA(new BigDecimal("1.45"))
                .coefficientTeamB(null)
                .isActive(true)
                .build();
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamBIsEmpty() {
        AddBetRequest request = AddBetRequest.builder()
                .teamAName("TeamAName")
                .teamBName("TeamBName")
                .coefficientTeamA(new BigDecimal("1.45"))
                .coefficientTeamB(BigDecimal.ZERO)
                .isActive(true)
                .build();
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamBIsLessThanOne() {
        AddBetRequest request = AddBetRequest.builder()
                .teamAName("TeamAName")
                .teamBName("TeamBName")
                .coefficientTeamA(new BigDecimal("1.45"))
                .coefficientTeamB(new BigDecimal("0.23"))
                .isActive(true)
                .build();
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must be greater than 1.00", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenCoefficientTeamBEqualsOne() {
        AddBetRequest request = AddBetRequest.builder()
                .teamAName("TeamAName")
                .teamBName("TeamBName")
                .coefficientTeamA(new BigDecimal("1.45"))
                .coefficientTeamB(new BigDecimal("1.00"))
                .isActive(true)
                .build();
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamAName")).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamBName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Coefficient", errors.get(0).getField());
        assertEquals("must be greater than 1.00", errors.get(0).getMessage());
    }

}