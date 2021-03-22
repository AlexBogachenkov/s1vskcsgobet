package s1vskcsgobet.core.responses.user_bet;

import lombok.Getter;
import s1vskcsgobet.core.domain.UserBet;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.CoreResponse;

import java.util.List;

@Getter
public class FindUserBetsByUserIdResponse extends CoreResponse {

    private List<UserBet> userBets;

    public FindUserBetsByUserIdResponse(List<CoreError> errors, List<UserBet> userBets) {
        super(errors);
        this.userBets = userBets;
    }

}
