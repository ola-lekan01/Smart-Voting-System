package africa.vote.SmartVote.datas.repositories;

import africa.vote.SmartVote.datas.models.OTPtoken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;

@Transactional
public interface TokenRepository extends JpaRepository<OTPtoken, Long> {
    @Modifying
    @Query("UPDATE OTPtoken otpToken " +
            "SET otpToken.confirmedTime = ?1 " +
            "WHERE otpToken.token = ?2")
    void setConfirmedAt(Instant confirmedTime, String token);
    OTPtoken findOTPtokenById(Long id);
    Optional<OTPtoken> findOTPtokenByToken(String token);

}
