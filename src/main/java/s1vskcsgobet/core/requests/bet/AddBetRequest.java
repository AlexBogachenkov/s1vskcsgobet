package s1vskcsgobet.core.requests.bet;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AddBetRequest {

    private String teamAName;
    private String teamBName;
    private BigDecimal coefficientTeamA;
    private BigDecimal coefficientTeamB;
    private boolean isActive;

}
