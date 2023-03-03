package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.dtos.requests.CreatePollRequest;
import africa.vote.SmartVote.datas.dtos.requests.VoteRequest;
import africa.vote.SmartVote.datas.models.Poll;

import java.util.List;

public interface PollService {
    String createPoll(CreatePollRequest createPollRequest);
    List<Poll> recentPolls();
    List<Poll>activePolls();
    String vote(Long pollId, VoteRequest voteRequest);
}
