package s1vskcsgobet.core.responses.team;

import lombok.Getter;
import s1vskcsgobet.core.domain.Team;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.CoreResponse;

import java.util.List;

@Getter
public class AddTeamResponse extends CoreResponse {

    private Team addedTeam;

    public AddTeamResponse(List<CoreError> errors) {
        super(errors);
    }

    public AddTeamResponse(Team addedTeam) {
        this.addedTeam = addedTeam;
    }

}
