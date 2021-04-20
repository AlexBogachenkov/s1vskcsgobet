package s1vskcsgobet.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class UserDto {

    private String nickname;
    private BigDecimal balance;

    public UserDto(String nickname, BigDecimal balance) {
        this.nickname = nickname;
        this.balance = balance;
    }

}
