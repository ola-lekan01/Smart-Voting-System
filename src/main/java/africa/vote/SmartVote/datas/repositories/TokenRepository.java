package africa.vote.SmartVote.datas.repositories;

import africa.vote.SmartVote.datas.models.OTPtoken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Transactional
public interface TokenRepository extends JpaRepository<OTPtoken, Long> {
    OTPtoken findOTPtokenById(Long id);
}
