package s1vskcsgobet.core.validators.user_bet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import s1vskcsgobet.core.database.BetRepository;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.requests.user_bet.ApplyMatchResultToUserBetsRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ApplyMatchResultToUserBetsRequestValidatorTest {

    @Mock
    private BetRepository betRepository;
    @Mock
    private TeamRepository teamRepository;
    @InjectMocks
    private ApplyMatchResultToUserBetsRequestValidator validator;

    @Test
    public void shouldReturnErrorList_whenBetNotFound() {
        ApplyMatchResultToUserBetsRequest request = new ApplyMatchResultToUserBetsRequest(2L, "teamName");
        Mockito.when(betRepository.existsById(2L)).thenReturn(false);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        Mockito.when(betRepository.countByIdAndTeamName(2L, "teamName")).thenReturn(1L);
        List<CoreError> errors = validator.validate(request);

        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertEquals("Bet ID", errors.get(0).getField());
        assertEquals("Bet with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorList_whenWinningTeamNameIsNull() {
        ApplyMatchResultToUserBetsRequest request = new ApplyMatchResultToUserBetsRequest(2L, null);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertEquals("Winning team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorList_whenWinningTeamNameIsEmpty() {
        ApplyMatchResultToUserBetsRequest request = new ApplyMatchResultToUserBetsRequest(2L, "");
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertEquals("Winning team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorList_whenWinningTeamNameIsBlank() {
        ApplyMatchResultToUserBetsRequest request = new ApplyMatchResultToUserBetsRequest(2L, "   ");
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertEquals("Winning team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorList_whenTeamNotFound() {
        ApplyMatchResultToUserBetsRequest request = new ApplyMatchResultToUserBetsRequest(2L, "teamName");
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertEquals("Winning team name", errors.get(0).getField());
        assertEquals("Team with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorList_whenBetNotFoundByTeamName() {
        ApplyMatchResultToUserBetsRequest request = new ApplyMatchResultToUserBetsRequest(2L, "teamName");
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        Mockito.when(betRepository.countByIdAndTeamName(2L, "teamName")).thenReturn(1L);
        List<CoreError> errors = validator.validate(request);

        assertNotNull(errors);
        assertEquals(1, errors.size());
        assertEquals("Winning team name", errors.get(0).getField());
        assertEquals("Bet with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors() {
        ApplyMatchResultToUserBetsRequest request = new ApplyMatchResultToUserBetsRequest(2L, "teamName");
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        Mockito.when(betRepository.countByIdAndTeamName(2L, "teamName")).thenReturn(1L);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

}