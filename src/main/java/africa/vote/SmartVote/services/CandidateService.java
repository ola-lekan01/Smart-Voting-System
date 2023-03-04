package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.models.Candidate;

import java.util.List;

public interface CandidateService {
    List<Candidate> findAllCandidatesResultOfAPoll(String pollId);
}
