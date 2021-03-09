package s1vskcsgobet.core.requests.bet;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddBetRequest {

    private String teamAName;
    private String teamBName;
    private BigDecimal coefficientTeamA;
    private BigDecimal coefficientTeamB;
    private boolean isActive;

}
