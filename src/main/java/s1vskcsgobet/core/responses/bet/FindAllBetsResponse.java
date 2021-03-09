package s1vskcsgobet.core.responses.bet;

import lombok.Getter;
import s1vskcsgobet.core.domain.Bet;
import s1vskcsgobet.core.responses.CoreResponse;

import java.util.List;

@Getter
public class FindAllBetsResponse extends CoreResponse {

    private List<Bet> allBets;

    public FindAllBetsResponse(List<Bet> allBets) {
        this.allBets = allBets;
    }

}