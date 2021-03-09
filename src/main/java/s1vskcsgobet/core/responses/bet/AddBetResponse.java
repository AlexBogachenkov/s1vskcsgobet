package s1vskcsgobet.core.responses.bet;

import lombok.Getter;
import s1vskcsgobet.core.domain.Bet;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.CoreResponse;

import java.util.List;

@Getter
public class AddBetResponse extends CoreResponse {

    private Bet addedBet;

    public AddBetResponse(List<CoreError> errors) {
        super(errors);
    }

    public AddBetResponse(Bet addedBet) {
        this.addedBet = addedBet;
    }

}
