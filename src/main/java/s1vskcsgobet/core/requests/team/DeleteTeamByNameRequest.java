package s1vskcsgobet.core.requests.team;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteTeamByNameRequest {

    private String teamName;

    public DeleteTeamByNameRequest(String teamName) {
        this.teamName = teamName;
    }

}
