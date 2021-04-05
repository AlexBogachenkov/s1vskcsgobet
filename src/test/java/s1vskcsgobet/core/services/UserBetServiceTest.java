package s1vskcsgobet.core.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import s1vskcsgobet.core.database.BetRepository;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.database.UserBetRepository;
import s1vskcsgobet.core.database.UserRepository;
import s1vskcsgobet.core.domain.*;
import s1vskcsgobet.core.requests.user_bet.AddUserBetRequest;
import s1vskcsgobet.core.requests.user_bet.FindUserBetsByUserIdRequest;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.user_bet.AddUserBetResponse;
import s1vskcsgobet.core.responses.user_bet.FindUserBetsByUserIdResponse;
import s1vskcsgobet.core.validators.user_bet.AddUserBetRequestValidator;
import s1vskcsgobet.core.validators.user_bet.FindUserBetsByUserIdRequestValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserBetServiceTest {

    @Mock
    private AddUserBetRequestValidator addUserBetRequestValidator;
    @Mock
    private FindUserBetsByUserIdRequestValidator findUserBetsByUserIdRequestValidator;
    @Mock
    private UserBetRepository userBetRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BetRepository betRepository;
    @Mock
    private UserService userService;
    @Mock
    private TeamRepository teamRepository;
    @InjectMocks
    private UserBetService userBetService;

    private List<CoreError> errors;
    private AddUserBetRequest addUserBetRequest;

    @BeforeEach
    public void setup() {
        errors = new ArrayList<>();
        addUserBetRequest = new AddUserBetRequest(1L, 2L, "teamName",
                new BigDecimal("1.23"), new BigDecimal("20"));
    }

    @Test
    public void shouldReturnErrorList_whenAddUserBetRequestValidationNotPassed() {
        errors.add(new CoreError("User ID", "must not be empty!"));
        Mockito.when(addUserBetRequestValidator.validate(addUserBetRequest)).thenReturn(errors);
        AddUserBetResponse response = userBetService.add(addUserBetRequest);

        assertNotNull(response.getErrors());
        assertEquals(1, response.getErrors().size());
        assertEquals("User ID", response.getErrors().get(0).getField());
        assertEquals("must not be empty!", response.getErrors().get(0).getMessage());
    }

    @Test
    public void shouldReturnAddedUserBet() {
        Mockito.when(addUserBetRequestValidator.validate(addUserBetRequest)).thenReturn(new ArrayList<>());
        User user = new User("nickname", "password", new BigDecimal("200"), Role.USER);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        Bet bet = new Bet(teamA, teamB, new BigDecimal("1.23"), new BigDecimal("2.34"), "Final", true);
        Team winningTeam = new Team("teamName");
        UserBet userBet = new UserBet(user, bet, winningTeam, addUserBetRequest.getWinningTeamCoefficient(), addUserBetRequest.getAmount(), UserBetStatus.PENDING);
        Mockito.when(userRepository.findById(addUserBetRequest.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(betRepository.findById(addUserBetRequest.getBetId())).thenReturn(Optional.of(bet));
        Mockito.when(teamRepository.findByNameIgnoreCase(addUserBetRequest.getWinningTeamName())).thenReturn(Optional.of(winningTeam));
        Mockito.when(userBetRepository.save(userBet)).thenReturn(userBet);
        AddUserBetResponse response = userBetService.add(addUserBetRequest);

        assertNull(response.getErrors());
        assertEquals(userBet, response.getAddedUserBet());
    }

    @Test
    public void shouldReturnErrorList_whenFindUserBetsByUserIdRequestValidationNotPassed() {
        FindUserBetsByUserIdRequest request = new FindUserBetsByUserIdRequest(null);
        errors.add(new CoreError("User ID", "must not be empty!"));
        Mockito.when(findUserBetsByUserIdRequestValidator.validate(request)).thenReturn(errors);
        FindUserBetsByUserIdResponse response = userBetService.findByUserId(request);

        assertNotNull(response.getErrors());
        assertEquals(1, response.getErrors().size());
        assertEquals("User ID", response.getErrors().get(0).getField());
        assertEquals("must not be empty!", response.getErrors().get(0).getMessage());
    }

    @Test
    public void shouldReturnFoundUserBets() {
        FindUserBetsByUserIdRequest request = new FindUserBetsByUserIdRequest(2L);
        Mockito.when(findUserBetsByUserIdRequestValidator.validate(request)).thenReturn(new ArrayList<>());
        UserBet userBet = new UserBet();
        UserBet userBet2 = new UserBet();
        List<UserBet> userBets = List.of(userBet, userBet2);
        Mockito.when(userBetRepository.findByUserIdOrderByIdDesc(2L)).thenReturn(userBets);
        FindUserBetsByUserIdResponse response = userBetService.findByUserId(request);

        assertNull(response.getErrors());
        assertEquals(2, response.getUserBets().size());
    }

}