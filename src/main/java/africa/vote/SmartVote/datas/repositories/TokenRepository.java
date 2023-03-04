package africa.vote.SmartVote.datas.repositories;

import africa.vote.SmartVote.datas.models.Token;
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
}
