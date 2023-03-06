package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.dtos.responses.CandidateResult;
import africa.vote.SmartVote.datas.models.Candidate;

import java.util.List;

public interface CandidateService {
    List<CandidateResult> findAllCandidatesResultOfAPoll(String pollId);
    void save(Candidate candidate);
}
