package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.responses.CandidateResult;
import africa.vote.SmartVote.datas.models.Candidate;
import africa.vote.SmartVote.datas.repositories.CandidateRepository;
import africa.vote.SmartVote.services.CandidateService;
import africa.vote.SmartVote.services.PollService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CandidateServiceImpl implements CandidateService {

    private final PollService pollService;

    private final CandidateRepository candidateRepository;

    public CandidateServiceImpl(PollService pollService,
                                CandidateRepository candidateRepository) {
        this.pollService = pollService;
        this.candidateRepository = candidateRepository;
    }

    @Override
    public List<CandidateResult> findAllCandidatesResultOfAPoll(String pollId) {
        var foundPoll = pollService.findPollById(pollId);
        List<CandidateResult> candidateResults = new ArrayList<>();

        for (int i = 0; i < foundPoll.getCandidates().size(); i++) {
            var candidateResult = CandidateResult.builder()
                    .candidateName(foundPoll.getCandidates().get(i).getName())
                    .candidateImageURL(foundPoll.getCandidates().get(i).getImageURL())
                    .candidateResult(foundPoll.getCandidates().get(i).getResult().getNoOfVotes())
                    .build();
            candidateResults.add(candidateResult);
        }
        return candidateResults;
    }

    @Override
    public void save(Candidate candidate) {
        candidateRepository.save(candidate);
    }
}