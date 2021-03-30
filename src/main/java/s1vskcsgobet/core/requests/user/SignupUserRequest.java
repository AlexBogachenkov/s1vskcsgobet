package s1vskcsgobet.core.requests.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupUserRequest {

    private String nickname;
    private String password;
    private String passwordAgain;

    public SignupUserRequest(String nickname, String password, String passwordAgain) {
        this.nickname = nickname;
        this.password = password;
        this.passwordAgain = passwordAgain;
    }

}
