package africa.vote.SmartVote.services.impl;

import africa.vote.SmartVote.datas.dtos.requests.CreatePollRequest;
import africa.vote.SmartVote.datas.enums.Category;
import africa.vote.SmartVote.datas.models.Poll;
import africa.vote.SmartVote.datas.repositories.PollRepository;
import africa.vote.SmartVote.exeptions.GenericException;
import africa.vote.SmartVote.services.PollService;
import africa.vote.SmartVote.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PollServiceImpl implements PollService {
    private final PollRepository pollRepository;
    private final UserService userService;

    @Autowired
    public PollServiceImpl(PollRepository pollRepository, UserService userService) {
        this.pollRepository = pollRepository;
        this.userService = userService;
    }

    @Override
    public String createPoll(CreatePollRequest createPollRequest) {
        var userEmail = userService.getUserName();
        var foundUser = userService.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("User Not found"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        "2023-04-01 08:00:00 24hrs"
        LocalDateTime startDateTime = LocalDateTime.parse(createPollRequest.getStartDateTime(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(createPollRequest.getEndDateTime(), formatter);

        if (endDateTime.isBefore(startDateTime))throw new GenericException("End date/time cant be before start date/time");
        if (startDateTime.isBefore(LocalDateTime.now()))throw new GenericException("Poll start date/time cant be before current date/time");
        if (endDateTime.isBefore(LocalDateTime.now()))throw new GenericException("Poll End date/time cant be before current date/time");
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
        return "Poll Successfully created";
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
                ||poll
                        .getStartDateTime()
                        .isBefore(LocalDateTime.now()))
                && poll.getCategory()
                        .equals(foundUser.getCategory())
                && poll.
                        getEndDateTime()
                        .isAfter(LocalDateTime.now()))
                .toList();
    }
}