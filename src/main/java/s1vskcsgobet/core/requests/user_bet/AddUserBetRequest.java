package s1vskcsgobet.core.requests.user_bet;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class AddUserBetRequest {

    private Long userId;
    private Long betId;
    private String winningTeamName;
    private BigDecimal winningTeamCoefficient;
    private BigDecimal amount;

    public AddUserBetRequest(Long userId, Long betId, String winningTeamName, BigDecimal winningTeamCoefficient, BigDecimal amount) {
        this.userId = userId;
        this.betId = betId;
        this.winningTeamName = winningTeamName;
        this.winningTeamCoefficient = winningTeamCoefficient;
        this.amount = amount;
    }

}
