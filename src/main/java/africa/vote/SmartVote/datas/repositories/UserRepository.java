package africa.vote.SmartVote.datas.repositories;

import africa.vote.SmartVote.datas.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmailIgnoreCase(String email);
}
