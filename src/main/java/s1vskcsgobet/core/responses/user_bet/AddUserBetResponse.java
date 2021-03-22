package s1vskcsgobet.core.responses.user_bet;

import lombok.Getter;
import s1vskcsgobet.core.domain.UserBet;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.CoreResponse;

import java.util.List;

@Getter
public class AddUserBetResponse extends CoreResponse {

    private UserBet addedUserBet;

    public AddUserBetResponse(List<CoreError> errors) {
        super(errors);
    }

    public AddUserBetResponse(UserBet addedUserBet) {
        this.addedUserBet = addedUserBet;
    }

}
