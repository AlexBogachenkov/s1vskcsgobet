package s1vskcsgobet.core.requests.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteUserByNicknameRequest {

    private String nickname;

    public DeleteUserByNicknameRequest(String nickname) {
        this.nickname = nickname;
    }

}
