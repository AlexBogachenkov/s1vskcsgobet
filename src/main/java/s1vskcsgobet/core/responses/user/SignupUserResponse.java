package s1vskcsgobet.core.responses.user;

import lombok.Getter;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.CoreResponse;

import java.util.List;

@Getter
public class SignupUserResponse extends CoreResponse {

    private boolean signedUp;

    public SignupUserResponse(List<CoreError> errors) {
        super(errors);
    }

    public SignupUserResponse(boolean signedUp) {
        this.signedUp = signedUp;
    }

}
