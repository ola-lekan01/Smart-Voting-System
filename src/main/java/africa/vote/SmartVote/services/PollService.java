package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.dtos.requests.CreatePollRequest;
import africa.vote.SmartVote.datas.dtos.requests.VoteRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiData;
import africa.vote.SmartVote.datas.models.Poll;

import java.util.List;

public interface PollService {
    ApiData createPoll(CreatePollRequest createPollRequest);
    List<Poll> recentPolls();
    List<Poll>activePolls();
    ApiData vote(String pollId, VoteRequest voteRequest);
    Poll findPollById(String pollId);
}
