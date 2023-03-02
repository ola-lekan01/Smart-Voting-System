package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.models.Result;
import africa.vote.SmartVote.datas.repositories.ResultRepository;
import africa.vote.SmartVote.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    private ResultRepository resultRepository;
    @Override
    public void updateCandidateResult(Long resultId) {
        Result foundResult = resultRepository.findById(resultId).get();
        foundResult.setNoOfVotes(foundResult
                .getNoOfVotes() + 1);
        resultRepository.save(foundResult);
    }
}
