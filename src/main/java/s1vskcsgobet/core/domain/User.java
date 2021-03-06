package s1vskcsgobet.core.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "nickname", nullable = false)
    private String nickname;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;
    @Column(name = "role", nullable = false)
    private String role;

    public User() {
    }

    public User(String nickname, String password, BigDecimal balance, String role) {
        this.nickname = nickname;
        this.password = password;
        this.balance = balance;
        this.role = role;
    }
}
