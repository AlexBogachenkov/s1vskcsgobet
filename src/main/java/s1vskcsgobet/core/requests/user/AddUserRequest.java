package s1vskcsgobet.core.requests.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import s1vskcsgobet.core.domain.Role;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class AddUserRequest {

    private String userNickname;
    private String userPassword;
    private BigDecimal userBalance;
    private Role userRole;

    public AddUserRequest(String userNickname, String userPassword, BigDecimal userBalance, Role userRole) {
        this.userNickname = userNickname;
        this.userPassword = userPassword;
        this.userBalance = userBalance;
        this.userRole = userRole;
    }

}
