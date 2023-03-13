package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.models.Vote;
import africa.vote.SmartVote.datas.repositories.VoteRepository;
import africa.vote.SmartVote.services.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    @Override
    public void saveUserVote(Vote vote) {
        voteRepository.save(vote);
    }

    @Override
    public List<Vote> findAllVotes() {
        return voteRepository.findAll();
    }
}