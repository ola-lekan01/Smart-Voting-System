package africa.vote.SmartVote.datas.repositories;

import africa.vote.SmartVote.datas.enums.Status;
import africa.vote.SmartVote.datas.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
@Transactional
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmailIgnoreCase(String email);
    @Modifying
    @Query("UPDATE User user " +
            "SET user.status = ?1 " +
            "WHERE user.email = ?2")
    void verifyUser(Status verify, String email);
    @Modifying
    @Query("DELETE FROM User user " +
            "WHERE user.status = :status")
    void deleteUnverifiedUsers(@Param("status") Status status);
}
