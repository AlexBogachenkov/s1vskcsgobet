package s1vskcsgobet.core.responses.user_bet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.CoreResponse;

import java.util.List;

@Getter
@NoArgsConstructor
public class ApplyMatchResultToUserBetsResponse extends CoreResponse {

    public ApplyMatchResultToUserBetsResponse(List<CoreError> errors) {
        super(errors);
    }

}
