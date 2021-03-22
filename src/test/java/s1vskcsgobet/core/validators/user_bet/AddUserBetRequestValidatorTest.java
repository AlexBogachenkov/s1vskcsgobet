package s1vskcsgobet.core.validators.user_bet;

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

    @Test
    public void shouldReturnErrorList_whenUserIdNotExists() {
        AddUserBetRequest request = new AddUserBetRequest(1L, 2L, "teamName",
                new BigDecimal("1.23"), new BigDecimal("20.00"));
        User user = new User("nickname", "password", new BigDecimal("200.00"), Role.USER);
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
        AddUserBetRequest request = new AddUserBetRequest(1L, 2L, "teamName",
                new BigDecimal("1.23"), new BigDecimal("20.00"));
        User user = new User("nickname", "password", new BigDecimal("200.00"), Role.USER);
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
        AddUserBetRequest request = new AddUserBetRequest(1L, 2L, null,
                new BigDecimal("1.23"), new BigDecimal("20.00"));
        User user = new User("nickname", "password", new BigDecimal("200.00"), Role.USER);
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
        AddUserBetRequest request = new AddUserBetRequest(1L, 2L, "",
                new BigDecimal("1.23"), new BigDecimal("20.00"));
        User user = new User("nickname", "password", new BigDecimal("200.00"), Role.USER);
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
        AddUserBetRequest request = new AddUserBetRequest(1L, 2L, "   ",
                new BigDecimal("1.23"), new BigDecimal("20.00"));
        User user = new User("nickname", "password", new BigDecimal("200.00"), Role.USER);
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
        AddUserBetRequest request = new AddUserBetRequest(1L, 2L, "teamName",
                new BigDecimal("1.23"), new BigDecimal("20.00"));
        User user = new User("nickname", "password", new BigDecimal("200.00"), Role.USER);
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
        AddUserBetRequest request = new AddUserBetRequest(1L, 2L, "teamName",
                null, new BigDecimal("200.00"));
        User user = new User("nickname", "password", new BigDecimal("200.00"), Role.USER);
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
        AddUserBetRequest request = new AddUserBetRequest(1L, 2L, "teamName",
                new BigDecimal("0.23"), new BigDecimal("200.00"));
        User user = new User("nickname", "password", new BigDecimal("200.00"), Role.USER);
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
        AddUserBetRequest request = new AddUserBetRequest(1L, 2L, "teamName",
                new BigDecimal("1.00"), new BigDecimal("200.00"));
        User user = new User("nickname", "password", new BigDecimal("200.00"), Role.USER);
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldReturnErrorList_whenAmountIsNull() {
        AddUserBetRequest request = new AddUserBetRequest(1L, 2L, "teamName",
                new BigDecimal("1.00"), null);
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
        AddUserBetRequest request = new AddUserBetRequest(1L, 2L, "teamName",
                new BigDecimal("1.00"), new BigDecimal("0.50"));
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
        AddUserBetRequest request = new AddUserBetRequest(1L, 2L, "teamName",
                new BigDecimal("1.00"), new BigDecimal("1.00"));
        User user = new User("nickname", "password", new BigDecimal("200.00"), Role.USER);
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldReturnErrorList_whenAmountIsMoreThanBalance() {
        AddUserBetRequest request = new AddUserBetRequest(1L, 2L, "teamName",
                new BigDecimal("1.00"), new BigDecimal("300.00"));
        User user = new User("nickname", "password", new BigDecimal("200.00"), Role.USER);
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
        AddUserBetRequest request = new AddUserBetRequest(1L, 2L, "teamName",
                new BigDecimal("1.00"), new BigDecimal("200.00"));
        User user = new User("nickname", "password", new BigDecimal("200.00"), Role.USER);
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

    @Test
    public void shouldNotReturnErrors() {
        AddUserBetRequest request = new AddUserBetRequest(1L, 2L, "teamName",
                new BigDecimal("1.00"), new BigDecimal("200.00"));
        User user = new User("nickname", "password", new BigDecimal("2000.00"), Role.USER);
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(betRepository.existsById(2L)).thenReturn(true);
        Mockito.when(teamRepository.existsByNameIgnoreCase("teamName")).thenReturn(true);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

}