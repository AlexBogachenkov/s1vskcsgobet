package s1vskcsgobet.core.validators.team;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.requests.team.DeleteTeamByNameRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteTeamByNameRequestValidatorTest {

    @Mock
    private TeamRepository teamRepository;
    @InjectMocks
    private DeleteTeamByNameRequestValidator validator;

    @Test
    public void shouldReturnError_whenNameIsNull() {
        DeleteTeamByNameRequest request = new DeleteTeamByNameRequest(null);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenNameIsEmpty() {
        DeleteTeamByNameRequest request = new DeleteTeamByNameRequest("");
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenNameIsBlank() {
        DeleteTeamByNameRequest request = new DeleteTeamByNameRequest("   ");
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenNameNotFound() {
        DeleteTeamByNameRequest request = new DeleteTeamByNameRequest("TeamName");
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamName")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("Team with this property not found.", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors() {
        DeleteTeamByNameRequest request = new DeleteTeamByNameRequest("TeamName");
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

}