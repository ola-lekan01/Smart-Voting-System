package africa.vote.SmartVote.services;

import africa.vote.SmartVote.datas.dtos.requests.CreatePollRequest;
import africa.vote.SmartVote.datas.models.Poll;

import java.util.List;

public interface PollService {
    String createPoll(Long userId, CreatePollRequest createPollRequest);
    List<Poll> recentPolls();
    List<Poll>activePolls(Long userId);
}
