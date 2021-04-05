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
import s1vskcsgobet.core.domain.Bet;
import s1vskcsgobet.core.domain.Team;
import s1vskcsgobet.core.requests.bet.AddBetRequest;
import s1vskcsgobet.core.requests.bet.DeleteBetByIdRequest;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.bet.AddBetResponse;
import s1vskcsgobet.core.responses.bet.DeleteBetByIdResponse;
import s1vskcsgobet.core.responses.bet.FindAllBetsResponse;
import s1vskcsgobet.core.validators.bet.AddBetRequestValidator;
import s1vskcsgobet.core.validators.bet.DeleteBetByIdRequestValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BetServiceTest {

    @Mock
    private BetRepository betRepository;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private AddBetRequestValidator addBetRequestValidator;
    @Mock
    private DeleteBetByIdRequestValidator deleteBetByIdRequestValidator;
    @InjectMocks
    private BetService betService;

    private List<CoreError> errors;
    private List<Bet> bets;
    private AddBetRequest addBetRequest;
    private Team teamA, teamB, teamC, teamD;
    private Bet betA, betB;

    @BeforeEach
    public void setup() {
        errors = new ArrayList<>();
        bets = new ArrayList<>();
        addBetRequest = new AddBetRequest("teamAName", "teamBName",
                new BigDecimal("1.23"), new BigDecimal("2.45"), "Final", true);
        teamA = new Team("teamA");
        teamB = new Team("teamB");
        teamC = new Team("teamC");
        teamD = new Team("teamD");
        betA = new Bet(teamA, teamB, new BigDecimal("1.23"), new BigDecimal("2.45"), "Final", true);
        betB = new Bet(teamC, teamD, new BigDecimal("2.32"), new BigDecimal("1.45"), "Final", true);
    }

    @Test
    public void shouldReturnErrorList_whenAddBetRequestValidationNotPassed() {
        addBetRequest.setTeamAName("");
        errors.add(new CoreError("Team A name", "must not be empty!"));
        Mockito.when(addBetRequestValidator.validate(addBetRequest)).thenReturn(errors);
        AddBetResponse response = betService.add(addBetRequest);

        assertTrue(response.hasErrors());
        assertEquals(1, response.getErrors().size());
        assertEquals("Team A name", response.getErrors().get(0).getField());
        assertEquals("must not be empty!", response.getErrors().get(0).getMessage());
    }

    @Test
    public void shouldReturnAddedBet() {
        Mockito.when(addBetRequestValidator.validate(addBetRequest)).thenReturn(new ArrayList<>());
        Mockito.when(teamRepository.findByNameIgnoreCase("teamAName")).thenReturn(Optional.of(teamA));
        Mockito.when(teamRepository.findByNameIgnoreCase("teamBName")).thenReturn(Optional.of(teamB));
        Bet bet = new Bet(teamA, teamB, addBetRequest.getCoefficientTeamA(), addBetRequest.getCoefficientTeamB(),
                addBetRequest.getStage(), addBetRequest.isActive());
        Mockito.when(betRepository.save(bet)).thenReturn(bet);
        AddBetResponse response = betService.add(addBetRequest);

        assertFalse(response.hasErrors());
        assertNotNull(response.getAddedBet());
    }

    @Test
    public void shouldReturnErrorList_whenDeleteBetByIdRequestValidationNotPassed() {
        DeleteBetByIdRequest request = new DeleteBetByIdRequest(null);
        errors.add(new CoreError("Team ID", "must not be empty!"));
        Mockito.when(deleteBetByIdRequestValidator.validate(request)).thenReturn(errors);
        DeleteBetByIdResponse response = betService.deleteById(request);

        assertTrue(response.hasErrors());
        assertEquals(1, response.getErrors().size());
        assertEquals("Team ID", response.getErrors().get(0).getField());
        assertEquals("must not be empty!", response.getErrors().get(0).getMessage());
    }

    @Test
    public void shouldReturnIsDeleted() {
        DeleteBetByIdRequest request = new DeleteBetByIdRequest(10L);
        Mockito.when(deleteBetByIdRequestValidator.validate(request)).thenReturn(new ArrayList<>());
        DeleteBetByIdResponse response = betService.deleteById(request);

        assertFalse(response.hasErrors());
        assertTrue(response.isDeleted());
    }

    @Test
    public void shouldReturnActiveBets() {
        bets.add(betA);
        bets.add(betB);
        Mockito.when(betRepository.findByIsActiveOrderByIdDesc(true)).thenReturn(bets);
        FindAllBetsResponse response = betService.findActive();

        assertFalse(response.hasErrors());
        assertEquals(2, response.getAllBets().size());
        assertEquals(teamA, response.getAllBets().get(0).getTeamA());
        assertEquals(teamC, response.getAllBets().get(1).getTeamA());
    }

    @Test
    public void shouldReturnAllBets() {
        betB.setActive(false);
        bets = List.of(betA, betB);
        Mockito.when(betRepository.findAllByOrderByIdDesc()).thenReturn(bets);
        FindAllBetsResponse response = betService.findAll();

        assertFalse(response.hasErrors());
        assertEquals(2, response.getAllBets().size());
        assertEquals(teamA, response.getAllBets().get(0).getTeamA());
        assertEquals(teamC, response.getAllBets().get(1).getTeamA());
    }

}