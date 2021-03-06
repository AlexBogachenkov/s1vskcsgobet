package s1vskcsgobet.core.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }
}
