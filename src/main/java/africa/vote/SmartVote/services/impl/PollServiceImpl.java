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

import java.time.LocalDate;
import java.time.LocalTime;
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

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

        //2023-04-01
        LocalDate startDate = LocalDate.parse(createPollRequest.getStartDate(), dateFormatter);
        LocalDate endDate = LocalDate.parse(createPollRequest.getEndDate(), dateFormatter);
        //"10:30:00 AM"
        LocalTime startTime = LocalTime.parse(createPollRequest.getStartTime(), timeFormatter);
        LocalTime endTime = LocalTime.parse(createPollRequest.getEndTime(), timeFormatter);

        if (endDate.isBefore(startDate))throw new GenericException("End date cant be before start start");
        if (endTime.isBefore(startTime))throw new GenericException("End time cant be before start time");
        if (endTime.equals(startTime))throw new GenericException("End time and start time cant be same");
        if (startDate.isBefore(LocalDate.now()))throw new GenericException("Poll start date cant be before current time");
        if (startTime.isBefore(LocalTime.now()))throw new GenericException("Poll start time cant be before current time");
        if (endDate.isBefore(LocalDate.now()))throw new GenericException("End date cant be before current date");
        if (endTime.isBefore(LocalTime.now()))throw new GenericException("End time cant be before current time");
        Poll poll = Poll.builder().
                title(createPollRequest.getTitle())
                .question(createPollRequest.getQuestion())
                .startDate(startDate)
                .startTime(startTime)
                .endDate(endDate)
                .endTime(endTime)
                .candidates(createPollRequest.getCandidates())
                .category(Category.getCategory(createPollRequest.getCategory()))
                .users(foundUser)
                .build();
        pollRepository.save(poll);
        return "Poll Successfully created";
    }

    @Override
    public List<Poll> recentPolls() {
        return pollRepository.findAll().stream().filter(poll -> (poll.getEndDate()
                .isBefore(LocalDate.now()) &&
                poll.getEndTime().isBefore(LocalTime.now())) ||
                (poll.getEndDate().equals(LocalDate.now()) &&
                poll.getEndTime().isBefore(LocalTime.now()))
                ).toList();
    }
    @Override
    public List<Poll> activePolls() {
        var userEmail = userService.getUserName();
        var foundUser = userService.findByEmailIgnoreCase(userEmail)
                .orElseThrow(()-> new GenericException("User Not found"));

        return pollRepository.findAll()
                .stream().filter(poll -> (poll.getEndTime().isAfter(LocalTime.now())
                && poll.getEndDate().isAfter(LocalDate.now())
                        &&poll.getCategory().equals(foundUser.getCategory()))
                ||(poll.getEndTime().isAfter(LocalTime.now())
                        && poll.getEndDate().equals(LocalDate.now())
                                &&poll.getCategory().equals(foundUser.getCategory()))
                ).toList();
    }
}