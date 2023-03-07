package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.responses.CandidateResult;
import africa.vote.SmartVote.datas.models.Candidate;
import africa.vote.SmartVote.datas.repositories.CandidateRepository;
import africa.vote.SmartVote.datas.repositories.PollRepository;
import africa.vote.SmartVote.exeptions.GenericException;
import africa.vote.SmartVote.services.CandidateService;
import africa.vote.SmartVote.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CandidateServiceImpl implements CandidateService {

    private final PollRepository pollRepository;
    private final CandidateRepository candidateRepository;
    private final UserService userService;

    @Autowired
    public CandidateServiceImpl(PollRepository pollRepository,
                                CandidateRepository candidateRepository,
                                UserService userService
                                ) {
        this.pollRepository = pollRepository;
        this.candidateRepository = candidateRepository;
        this.userService = userService;
    }

    @Override
    public List<CandidateResult> findAllCandidatesResultOfAPoll(String pollId) {

        var foundPoll = pollRepository.findById(pollId)
                .orElseThrow(()-> new GenericException("Poll Id Does not Exist"));

        var userEmail = userService.getUserName();
        var foundUser = userService.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("AppUser Not found"));

        if(foundUser != foundPoll.getUsers()) throw new GenericException("This result is not available for logged in user");

        List<CandidateResult> candidateResults = new ArrayList<>();

        for (int i = 0; i < foundPoll.getCandidates().size(); i++) {
            var candidateResult = CandidateResult.builder()
                    .candidateName(foundPoll.getCandidates().get(i).getName())
                    .candidateImageURL(foundPoll.getCandidates().get(i).getImageURL())
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
}