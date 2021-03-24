package s1vskcsgobet.core.requests.user_bet;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApplyMatchResultToUserBetsRequest {

    private Long betId;
    private String winningTeamName;

    public ApplyMatchResultToUserBetsRequest(Long betId, String winningTeamName) {
        this.betId = betId;
        this.winningTeamName = winningTeamName;
    }

}
