package s1vskcsgobet.core.requests.bet;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class AddBetRequest {

    private String teamAName;
    private String teamBName;
    private BigDecimal coefficientTeamA;
    private BigDecimal coefficientTeamB;
    private boolean isActive;

    public AddBetRequest(String teamAName, String teamBName,
                         BigDecimal coefficientTeamA, BigDecimal coefficientTeamB, boolean isActive) {
        this.teamAName = teamAName;
        this.teamBName = teamBName;
        this.coefficientTeamA = coefficientTeamA;
        this.coefficientTeamB = coefficientTeamB;
        this.isActive = isActive;
    }

}
