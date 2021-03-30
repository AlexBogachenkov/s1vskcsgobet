package s1vskcsgobet.core.responses.user;

import lombok.Getter;
import s1vskcsgobet.core.responses.CoreError;
import s1vskcsgobet.core.responses.CoreResponse;

import java.util.List;

@Getter
public class LoginUserResponse extends CoreResponse {

    private boolean loggedIn;

    public LoginUserResponse(List<CoreError> errors) {
        super(errors);
    }

    public LoginUserResponse(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

}
