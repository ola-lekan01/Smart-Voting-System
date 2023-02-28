package africa.vote.SmartVote.datas.repositories;

import africa.vote.SmartVote.datas.enums.Status;
import africa.vote.SmartVote.datas.models.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
@Transactional
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmailIgnoreCase(String email);
    Optional<Users> findUsersById(Long id);
    @Modifying
    @Query("UPDATE Users users " +
            "SET users.status = ?1 " +
            "WHERE users.email = ?2")
    void verifyUser(Status verify, String email);
}
