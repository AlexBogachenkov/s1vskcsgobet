package s1vskcsgobet.core.validators.user_bet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import s1vskcsgobet.core.database.BetRepository;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.domain.Role;
import s1vskcsgobet.core.domain.User;
import s1vskcsgobet.core.requests.user_bet.AddUserBetRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddUserBetRequestValidatorTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BetRepository betRepository;
    @Mock
    private TeamRepository teamRepository;
    @InjectMocks
    private AddUserBetRequestValidator validator;

    private AddUserBetRequest request;
    private User user;

    @BeforeEach
    public void setup() {
        request = new AddUserBetRequest(1L, 2L, "teamName",
                new BigDecimal("1.23"), new BigDecimal("20.00"));
        user = new User("nickname", "password", new BigDecimal("200.00"), Role.USER);
    }

    @Test
    public void shouldReturnErrorList_whenUserIdNotExists() {
        Mockito.when(userRepository.existsById(1L)).thenReturn(false);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("User ID", errors.get(0).getField());
        assertEquals("User with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorList_whenBetIdNotExists() {
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(false);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Bet ID", errors.get(0).getField());
        assertEquals("Bet with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorList_whenWinningTeamNameIsNull() {
        request.setWinningTeamName(null);
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Winning team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorList_whenWinningTeamNameIsEmpty() {
        request.setWinningTeamName("");
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Winning team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorList_whenWinningTeamNameIsBlank() {
        request.setWinningTeamName("   ");
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Winning team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorList_whenTeamNameNotExists() {
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(false);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("Team with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorList_whenWinningTeamCoefficientIsNull() {
        request.setWinningTeamCoefficient(null);
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Winning team coefficient", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorList_whenWinningTeamCoefficientIsLessThanOne() {
        request.setWinningTeamCoefficient(new BigDecimal("0.23"));
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Winning team coefficient", errors.get(0).getField());
        assertEquals("must be equal to or greater than 1.00.", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors_whenWinningTeamCoefficientEqualsOne() {
        request.setWinningTeamCoefficient(new BigDecimal("1.00"));
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldReturnErrorList_whenAmountIsNull() {
        request.setAmount(null);
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Bet amount", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnErrorList_whenAmountIsLessThanOne() {
        request.setAmount(new BigDecimal("0.50"));
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Bet amount", errors.get(0).getField());
        assertEquals("must be equal to or greater than 1.00 JURCOIN.", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors_whenAmountEqualsOne() {
        request.setAmount(new BigDecimal("1.00"));
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldReturnErrorList_whenAmountIsMoreThanBalance() {
        request.setAmount(new BigDecimal("300.00"));
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Bet amount", errors.get(0).getField());
        assertEquals("must not be greater than balance.", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors_whenAmountEqualsBalance() {
        request.setAmount(new BigDecimal("200.00"));
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldNotReturnErrors() {
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

}