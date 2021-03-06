package s1vskcsgobet.core.responses.team;

import lombok.Getter;
import s1vskcsgobet.core.domain.Team;
import s1vskcsgobet.core.responses.CoreResponse;

import java.util.List;
@Getter
public class FindAllTeamsResponse extends CoreResponse {

    private List<Team> allTeams;

    public FindAllTeamsResponse(List<Team> allTeams) {
        this.allTeams = allTeams;
    }

}
