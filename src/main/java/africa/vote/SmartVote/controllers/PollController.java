package africa.vote.SmartVote.controllers;

import africa.vote.SmartVote.datas.dtos.requests.CreatePollRequest;
import africa.vote.SmartVote.datas.dtos.requests.VoteRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiResponse;
import africa.vote.SmartVote.services.PollService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("/api/v1/poll/")
public class PollController {
    private final PollService pollService;

    @Autowired
    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @PostMapping("create")
    public ResponseEntity<?> createPoll(@Valid @RequestBody CreatePollRequest createPollRequest,
                                        HttpServletRequest request) {

        String createdPoll = pollService.createPoll(createPollRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(createdPoll)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("active")

    public ResponseEntity<?> activePolls(HttpServletRequest request) {
        var activePolls = pollService.activePolls();
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(activePolls)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("recent")
    public ResponseEntity<?> recentPolls(HttpServletRequest request) {
        var recentPolls = pollService.recentPolls();
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(recentPolls)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @PutMapping("vote/{poll_id}")
    public ResponseEntity<?> vote(@PathVariable("poll_id") Long pollId, @Valid @RequestBody VoteRequest voteRequest,
                                  HttpServletRequest request) {

        String vote = pollService.vote(pollId, voteRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(vote)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}