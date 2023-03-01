package africa.vote.SmartVote.datas.repositories;

import africa.vote.SmartVote.datas.models.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {
}
