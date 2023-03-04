package africa.vote.SmartVote.datas.repositories;

import africa.vote.SmartVote.datas.dtos.responses.ApiData;
import africa.vote.SmartVote.datas.models.Token;
import africa.vote.SmartVote.datas.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
public interface TokenRepository extends JpaRepository<Token, String> {
    @Modifying
    @Query("UPDATE Token token " +
            "SET token.confirmedTime = ?1 " +
            "WHERE token.id = ?2")
    void setConfirmedAt(LocalDateTime confirmedAt, String tokenId);

    Optional<Token> findByToken(String token);

    Optional<Token> findByUserId(String id);
    void deleteTokenByConfirmedTimeIsBefore(LocalDateTime now);
    @Modifying
    @Query("UPDATE Token token " +
            "SET token.user = NULL " +
            "WHERE token.user = :deleted_Id")
    void updateTokenForDeletedUnverifiedUsers(User deleted_Id);

    @Modifying
    @Query("DELETE FROM Token token " +
            "WHERE token.confirmedTime = NULL")
    void deleteUnconfirmedToken();
}
