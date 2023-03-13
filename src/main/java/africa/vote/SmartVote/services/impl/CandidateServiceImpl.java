package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.responses.CandidateResult;
import africa.vote.SmartVote.datas.models.Candidate;
import africa.vote.SmartVote.datas.repositories.CandidateRepository;
import africa.vote.SmartVote.datas.repositories.PollRepository;
import africa.vote.SmartVote.exeptions.GenericException;
import africa.vote.SmartVote.services.CandidateService;
import africa.vote.SmartVote.services.PollService;
import africa.vote.SmartVote.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final UserService userService;
    private final PollRepository pollRepository;

    @Override
    public List<CandidateResult> findAllCandidatesResultOfAPoll(String pollId) {

        var foundPoll = pollRepository.findById(pollId)
                .orElseThrow(()-> new GenericException(String.format("%s does not exist", pollId)));
        var userEmail = userService.getUserName();
        var foundUser = userService.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("AppUser Not found"));

        if(!Objects.equals(foundUser.getId(), foundPoll.getUsers().getId()))
            throw new GenericException("This result is not available for logged in user");

        List<CandidateResult> candidateResults = new ArrayList<>();

        for (int i = 0; i < foundPoll.getCandidates().size(); i++) {
            var candidateResult = CandidateResult.builder()
                    .candidateName(foundPoll.getCandidates().get(i).getName())
                    .candidateResult(foundPoll.getCandidates().get(i).getResult().getNoOfVotes())
                    .pollId(foundPoll.getId())
                    .build();
            candidateResults.add(candidateResult);
        }
        return candidateResults;
    }

    @Override
    public Candidate save(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate findById(String candidateId) {
        return candidateRepository.findById(candidateId).orElseThrow(()->new GenericException("Candidate not found"));
    }
}