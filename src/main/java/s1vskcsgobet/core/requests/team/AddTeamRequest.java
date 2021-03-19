package s1vskcsgobet.core.requests.team;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddTeamRequest {

    private String teamName;

    public AddTeamRequest(String teamName) {
        this.teamName = teamName;
    }

}
