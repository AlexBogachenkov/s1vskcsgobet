package s1vskcsgobet.core.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import s1vskcsgobet.core.domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    int deleteByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

}
