package s1vskcsgobet.core.requests.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUserRequest {

    private String nickname;
    private String password;

    public LoginUserRequest(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

}
