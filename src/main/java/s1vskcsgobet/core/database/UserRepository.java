package s1vskcsgobet.core.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import s1vskcsgobet.core.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    int deleteByNicknameIgnoreCase(String nickname);

    boolean existsByNicknameIgnoreCase(String nickname);

}