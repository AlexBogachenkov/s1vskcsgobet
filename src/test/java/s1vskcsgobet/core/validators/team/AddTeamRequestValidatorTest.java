package s1vskcsgobet.core.validators.team;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import s1vskcsgobet.core.database.TeamRepository;
import s1vskcsgobet.core.requests.team.AddTeamRequest;
import s1vskcsgobet.core.responses.CoreError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddTeamRequestValidatorTest {

    @Mock
    private TeamRepository teamRepository;
    @InjectMocks
    private AddTeamRequestValidator validator;

    private AddTeamRequest request;

    @BeforeEach
    public void setup() {
        request = new AddTeamRequest("TeamName");
    }

    @Test
    public void shouldReturnError_whenNameIsNull() {
        request.setTeamName(null);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenNameIsEmpty() {
        request.setTeamName("");
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenNameIsBlank() {
        request.setTeamName("   ");
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("must not be empty!", errors.get(0).getMessage());
    }

    @Test
    public void shouldReturnError_whenNameAlreadyExists() {
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamName")).thenReturn(true);
        List<CoreError> errors = validator.validate(request);

        assertEquals(1, errors.size());
        assertEquals("Team name", errors.get(0).getField());
        assertEquals("Team with this property already exists.", errors.get(0).getMessage());
    }

    @Test
    public void shouldNotReturnErrors() {
        Mockito.when(teamRepository.existsByNameIgnoreCase("TeamName")).thenReturn(false);
        List<CoreError> errors = validator.validate(request);

        assertEquals(0, errors.size());
    }

}