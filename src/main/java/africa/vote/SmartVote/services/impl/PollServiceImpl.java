package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.requests.CreatePollRequest;
import africa.vote.SmartVote.datas.dtos.requests.VoteRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiData;
import africa.vote.SmartVote.datas.enums.Category;
import africa.vote.SmartVote.datas.models.Candidate;
import africa.vote.SmartVote.datas.models.Poll;
import africa.vote.SmartVote.datas.models.Result;
import africa.vote.SmartVote.datas.models.Vote;
import africa.vote.SmartVote.datas.repositories.PollRepository;
import africa.vote.SmartVote.exeptions.GenericException;
import africa.vote.SmartVote.services.PollService;
import africa.vote.SmartVote.services.ResultService;
import africa.vote.SmartVote.services.UserService;
import africa.vote.SmartVote.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PollServiceImpl implements PollService {
    private final PollRepository pollRepository;
    private final UserService userService;

    private final ResultService resultService;
    private final VoteService voteService;

    @Autowired
    public PollServiceImpl(PollRepository pollRepository,
                           UserService userService,
                           ResultService resultService,
                           VoteService voteService) {
        this.pollRepository = pollRepository;
        this.userService = userService;
        this.resultService = resultService;
        this.voteService = voteService;
    }

    @Override
    public ApiData createPoll(CreatePollRequest createPollRequest) {
        var userEmail = userService.getUserName();
        var foundUser = userService.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("User Not found"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//       "2023-04-01 08:00:00 24hrs"
        LocalDateTime startDateTime = LocalDateTime.parse(createPollRequest.getStartDateTime(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(createPollRequest.getEndDateTime(), formatter);

        if (endDateTime.isBefore(startDateTime))throw new GenericException("End date/time cant be before start date/time");
        if (startDateTime.isBefore(LocalDateTime.now()))throw new GenericException("Poll start date/time cant be before current date/time");
        if (endDateTime.isBefore(LocalDateTime.now()))throw new GenericException("Poll End date/time cant be before current date/time");
        for (Candidate candidate: createPollRequest.getCandidates()) {
            Result result = new Result();
            result.setNoOfVotes(0L);
            candidate.setResult(result);
        }
        Poll poll = Poll.builder().
                title(createPollRequest.getTitle())
                .question(createPollRequest.getQuestion())
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .candidates(createPollRequest.getCandidates())
                .category(Category.getCategory(createPollRequest.getCategory()))
                .users(foundUser)
                .build();
        pollRepository.save(poll);
        return ApiData.builder()
                .data("Poll Successfully Created!!! ")
                .build();
    }

    @Override
    public List<Poll> recentPolls() {
        return pollRepository.findAll()
                .stream()
                .filter(poll -> poll
                        .getEndDateTime()
                        .isBefore(LocalDateTime.now()))
                .toList();
    }
    @Override
    public List<Poll> activePolls() {
        var userEmail = userService.getUserName();
        var foundUser = userService.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("User Not found"));

        return pollRepository.findAll()
                .stream()
                .filter(poll -> (poll
                        .getStartDateTime()
                        .equals(LocalDateTime.now())
                || poll
                        .getStartDateTime()
                        .isBefore(LocalDateTime.now()))
                && poll.getCategory()
                        .equals(foundUser.getCategory())
                && poll.
                        getEndDateTime()
                        .isAfter(LocalDateTime.now()))
                .toList();
    }
    @Transactional
    @Override
    public ApiData vote(String pollId, VoteRequest voteRequest) {
        var userEmail = userService.getUserName();
        var foundUser = userService.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("User Not found"));

        Poll foundPoll = pollRepository.findById(pollId)
                .orElseThrow(()-> new GenericException("Poll Id Does not Exist! "));

        for (Vote vote: voteService.findAllVotes()) {
            boolean votedBefore = vote.getPolls().contains(foundPoll) && vote.getUsers().contains(foundUser) && vote.isVoted();
            if (votedBefore)throw new GenericException("You can't vote twice");
        }
        List<Candidate> foundPollCandidates = foundPoll.getCandidates();

        for (Candidate candidate: foundPollCandidates) {
            if (candidate.getId().equals(voteRequest.getCandidateId())){
//                String resultId = candidate.getResult().getId();
//                resultService.updateCandidateResult(resultId);
                Vote vote = new Vote();
                vote.getPolls().add(foundPoll);
                vote.getUsers().add(foundUser);
                vote.setVoted(true);
                voteService.saveUserVote(vote);
            }
        }
        return ApiData.builder()
                .data("You have successfully casted your vote!!! ")
                .build();
    }
}