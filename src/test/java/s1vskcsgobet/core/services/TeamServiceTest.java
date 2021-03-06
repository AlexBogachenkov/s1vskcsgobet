package s1vskcsgobet.core.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.domain.Team;
import s1vskcsgobet.core.requests.team.AddTeamRequest;
import s1vskcsgobet.core.requests.team.DeleteTeamByNameRequest;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.team.AddTeamResponse;
import s1vskcsgobet.core.responses.team.DeleteTeamByNameResponse;
import s1vskcsgobet.core.responses.team.FindAllTeamsResponse;
import s1vskcsgobet.core.validators.team.AddTeamRequestValidator;
import s1vskcsgobet.core.validators.team.DeleteTeamByNameRequestValidator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;
    @Mock
    private AddTeamRequestValidator addTeamRequestValidator;
    @Mock
    private DeleteTeamByNameRequestValidator deleteTeamByNameRequestValidator;
    @InjectMocks
    private TeamService teamService;

    private List<CoreError> errors;
    private Team teamA;
    private Team teamB;

    @BeforeEach
    public void setup() {
        errors = new ArrayList<>();
        teamA = new Team("teamA");
        teamB = new Team("teamB");
    }

    @Test
    public void shouldReturnErrorList_whenAddTeamRequestValidationNotPassed() {
        AddTeamRequest request = new AddTeamRequest("");
        errors.add(new CoreError("Team name", "must not be empty!"));
        Mockito.when(addTeamRequestValidator.validate(request)).thenReturn(errors);
        AddTeamResponse response = teamService.add(request);

        assertTrue(response.hasErrors());
        assertEquals(1, response.getErrors().size());
        assertEquals("Team name", response.getErrors().get(0).getField());
        assertEquals("must not be empty!", response.getErrors().get(0).getMessage());
    }

    @Test
    public void shouldReturnAddedTeam() {
        AddTeamRequest request = new AddTeamRequest(teamA.getName());
        Mockito.when(addTeamRequestValidator.validate(request)).thenReturn(new ArrayList<>());
        Mockito.when(teamRepository.save(teamA)).thenReturn(teamA);
        AddTeamResponse response = teamService.add(request);

        assertFalse(response.hasErrors());
        assertNotNull(response.getAddedTeam());
        assertEquals("teamA", response.getAddedTeam().getName());
    }

    @Test
    public void shouldReturnErrorList_whenDeleteTeamByNameRequestValidationNotPassed() {
        DeleteTeamByNameRequest request = new DeleteTeamByNameRequest("");
        errors.add(new CoreError("Team name", "must not be empty!"));
        Mockito.when(deleteTeamByNameRequestValidator.validate(request)).thenReturn(errors);
        DeleteTeamByNameResponse response = teamService.deleteByName(request);

        assertTrue(response.hasErrors());
        assertEquals(1, response.getErrors().size());
        assertEquals("Team name", response.getErrors().get(0).getField());
        assertEquals("must not be empty!", response.getErrors().get(0).getMessage());
    }

    @Test
    public void shouldReturnIsDeleted() {
        DeleteTeamByNameRequest request = new DeleteTeamByNameRequest(teamA.getName());
        Mockito.when(deleteTeamByNameRequestValidator.validate(request)).thenReturn(new ArrayList<>());
        Mockito.when(teamRepository.deleteByNameIgnoreCase(teamA.getName())).thenReturn(1);
        DeleteTeamByNameResponse response = teamService.deleteByName(request);

        assertFalse(response.hasErrors());
        assertTrue(response.isDeleted());
    }

    @Test
    public void shouldReturnAllTeams() {
        List<Team> allTeams = List.of(teamA, teamB);
        Mockito.when(teamRepository.findAll()).thenReturn(allTeams);
        FindAllTeamsResponse response = teamService.findAll();

        assertFalse(response.hasErrors());
        assertEquals(2, response.getAllTeams().size());
        assertEquals("teamA", response.getAllTeams().get(0).getName());
        assertEquals("teamB", response.getAllTeams().get(1).getName());
    }

}