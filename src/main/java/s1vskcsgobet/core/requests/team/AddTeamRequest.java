package s1vskcsgobet.core.requests.team;

import lombok.Data;

@Data
public class AddTeamRequest {

    private String teamName;

    public AddTeamRequest(String teamName) {
        this.teamName = teamName;
    }

}
