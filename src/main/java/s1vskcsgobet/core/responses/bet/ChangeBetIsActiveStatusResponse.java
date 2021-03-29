package s1vskcsgobet.core.responses.bet;

import lombok.Getter;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.CoreResponse;

import java.util.List;

@Getter
public class ChangeBetIsActiveStatusResponse extends CoreResponse {

    private boolean updated;

    public ChangeBetIsActiveStatusResponse(List<CoreError> errors) {
        super(errors);
    }

    public ChangeBetIsActiveStatusResponse(boolean updated) {
        this.updated = updated;
    }

}
