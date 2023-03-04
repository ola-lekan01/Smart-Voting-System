package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.models.Candidate;
import africa.vote.SmartVote.datas.models.Poll;
import africa.vote.SmartVote.services.CandidateService;
import africa.vote.SmartVote.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CandidateServiceImpl implements CandidateService {
    @Autowired
    private PollService pollService;
    @Override
    public List<Candidate> findAllCandidatesResultOfAPoll(String pollId) {
        Poll foundPoll = pollService.findPollById(pollId);
        return foundPoll.getCandidates();
    }
}
