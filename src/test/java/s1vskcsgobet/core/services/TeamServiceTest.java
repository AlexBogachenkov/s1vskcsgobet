package s1vskcsgobet.core.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.domain.Team;
import s1vskcsgobet.core.requests.team.AddTeamRequest;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.team.AddTeamResponse;
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

    @Test
    public void shouldReturnErrorList_whenAddTeamRequestValidationNotPassed() {
        AddTeamRequest request = new AddTeamRequest("");
        List<CoreError> errors = new ArrayList<>();
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
        Team team = new Team("teamName");
        AddTeamRequest request = new AddTeamRequest(team.getName());
        Mockito.when(addTeamRequestValidator.validate(request)).thenReturn(new ArrayList<>());
        Mockito.when(teamRepository.save(team)).thenReturn(team);
        AddTeamResponse response = teamService.add(request);

        assertFalse(response.hasErrors());
        assertEquals("teamName", response.getAddedTeam().getName());
    }

}