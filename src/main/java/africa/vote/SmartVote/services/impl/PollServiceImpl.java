package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.requests.CreatePollRequest;
import africa.vote.SmartVote.datas.dtos.requests.VoteRequest;
import africa.vote.SmartVote.datas.dtos.responses.*;
import africa.vote.SmartVote.datas.enums.Category;
import africa.vote.SmartVote.datas.models.Candidate;
import africa.vote.SmartVote.datas.models.Poll;
import africa.vote.SmartVote.datas.models.Result;
import africa.vote.SmartVote.datas.models.Vote;
import africa.vote.SmartVote.datas.repositories.CandidateRepository;
import africa.vote.SmartVote.datas.repositories.PollRepository;
import africa.vote.SmartVote.exeptions.GenericException;
import africa.vote.SmartVote.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PollServiceImpl implements PollService {
    private final PollRepository pollRepository;
    private final UserService userService;
    private final ResultService resultService;
    private final VoteService voteService;
    private final CandidateRepository candidateRepository;

    @Autowired
    public PollServiceImpl(PollRepository pollRepository,
                           UserService userService,
                           ResultService resultService,
                           VoteService voteService,
                           CandidateRepository candidateRepository) {
        this.pollRepository = pollRepository;
        this.userService = userService;
        this.resultService = resultService;
        this.voteService = voteService;
        this.candidateRepository = candidateRepository;
    }

    @Override
    public ApiData createPoll(CreatePollRequest createPollRequest) {
        var candidate = new Candidate();
        List<Candidate> candidateLists = new ArrayList<>();

        var userEmail = userService.getUserName();
        var foundUser = userService.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("User Not found"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//       "2023-04-01 08:00:00 24hrs"
        LocalDateTime startDateTime = LocalDateTime.parse(createPollRequest.getStartDateTime(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(createPollRequest.getEndDateTime(), formatter);

        //Building Candidates from Poll Request
        for (int i = 0; i < createPollRequest.getCandidates().size(); i++) {
            candidate.setName(createPollRequest.getCandidates().get(i).getCandidateName());
            candidate.setImageURL(createPollRequest.getCandidates().get(i).getCandidateImageURL());
            candidateRepository.save(candidate);
            candidateLists.add(candidate);
        }

        if (endDateTime.isBefore(startDateTime))throw new GenericException("End date/time cant be before start date/time");
        if (startDateTime.isBefore(LocalDateTime.now()))throw new GenericException("Poll start date/time cant be before current date/time");
        if (endDateTime.isBefore(LocalDateTime.now()))throw new GenericException("Poll End date/time cant be before current date/time");

        for (Candidate candidates: candidateLists) {
            Result result = new Result();
            result.setNoOfVotes(0L);
            candidates.setResult(result);
        }

        Poll poll = Poll.builder()
                .title(createPollRequest.getTitle())
                .question(createPollRequest.getQuestion())
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .candidates(candidateLists)
                .category(Category.getCategory(createPollRequest.getCategory()))
                .users(foundUser)
                .build();
        var savedPoll = pollRepository.save(poll);

        return ApiData.builder()
                .data("Poll Successfully Created!!! ")
                .pollId(savedPoll.getId())
                .build();
    }

    @Override
    public List<RecentPoll> recentPolls() {
        List<RecentPoll> recentPolls = new ArrayList<>();
        List<CandidateResult> candidateResult = new ArrayList<>();
        var foundPolls =  pollRepository.findAll()
                .stream()
                .filter(poll -> poll
                        .getEndDateTime()
                        .isBefore(LocalDateTime.now()))
                .toList();

        for (int i = 0; i < foundPolls.size(); i++) {
            CandidateResult.builder()
                    .candidateImageURL(foundPolls.get(i).getCandidates().get(i).getImageURL())
                    .candidateName(foundPolls.get(i).getCandidates().get(i).getName())
                    .candidateResult(foundPolls.get(i).getCandidates().get(i).getResult().getNoOfVotes())
                    .build();
        }

        for (Poll foundPoll : foundPolls) {
            RecentPoll.builder()
                    .title(foundPoll.getTitle())
                    .question(foundPoll.getQuestion())
                    .startDateTime(foundPoll.getStartDateTime())
                    .endDateTime(foundPoll.getEndDateTime())
                    .category(foundPoll.getCategory())
                    .candidates(candidateResult)
                    .build();
        }
        return recentPolls;
    }
    @Override
    public List<ActivePoll> activePolls() {
        List<ActivePoll> pollList = new ArrayList<>();
        List<CandidateResponse> candidateList = new ArrayList<>();

        var userEmail = userService.getUserName();
        var foundUser = userService.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("User Not found"));

        var foundPoll = pollRepository.findAll()
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

        for (int i = 0; i < foundPoll.size(); i++) {
            CandidateResponse.builder()
                    .candidateName(foundPoll.get(i).getCandidates().get(i).getName())
                    .candidateImageURL(foundPoll.get(i).getCandidates().get(i).getImageURL())
                    .candidateId(foundPoll.get(i).getCandidates().get(i).getId())
                    .build();
        }

        for (Poll value : foundPoll) {
            ActivePoll.builder()
                    .title(value.getTitle())
                    .question(value.getQuestion())
                    .startDateTime(value.getStartDateTime())
                    .endDateTime(value.getEndDateTime())
                    .category(value.getCategory())
                    .candidates(candidateList)
                    .build();
        }
        return pollList;
    }

    @Transactional
    @Override
    public ApiData vote(String pollId, VoteRequest voteRequest) {
        var userEmail = userService.getUserName();
        var foundUser = userService.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("User Not found"));

        Poll foundPoll = findPollById(pollId);

        for (Vote vote: voteService.findAllVotes()) {
            boolean votedBefore = vote.getPolls().contains(foundPoll) && vote.getUsers().contains(foundUser) && vote.isVoted();
            if (votedBefore)throw new GenericException("You can't vote twice");
        }
        List<Candidate> foundPollCandidates = foundPoll.getCandidates();

        for (Candidate candidate: foundPollCandidates) {
            if (candidate.getId().equals(voteRequest.getCandidateId())){
                String resultId = candidate.getResult().getId();
                resultService.updateCandidateResult(resultId);
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

    @Override
    public Poll findPollById(String pollId) {
        return pollRepository.findById(pollId)
                .orElseThrow(()-> new GenericException("Poll Id Does not Exist! "));
    }
}