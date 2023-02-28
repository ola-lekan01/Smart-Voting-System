package africa.vote.SmartVote.datas.repositories;

import africa.vote.SmartVote.datas.enums.Status;
import africa.vote.SmartVote.datas.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
    @Modifying
    @Query("UPDATE User users " +
            "SET users.status = ?1" +
            "WHERE users.email = ?2")
    void verifyUser(Status verify, String email);
}
