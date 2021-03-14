package s1vskcsgobet.core.requests.team;

import lombok.Data;

@Data
public class DeleteTeamByNameRequest {

    private String teamName;

    public DeleteTeamByNameRequest(String teamName) {
        this.teamName = teamName;
    }

}
