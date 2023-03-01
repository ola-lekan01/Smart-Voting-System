package africa.vote.SmartVote.controllers;

import africa.vote.SmartVote.datas.dtos.requests.CreatePollRequest;
import africa.vote.SmartVote.datas.dtos.responses.ApiResponse;
import africa.vote.SmartVote.services.PollService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/poll")
public class Poll {
    @Autowired
    private PollService pollService;

    @PostMapping("/create-poll/{user_id}")
    public ResponseEntity<?> createPoll(@PathVariable("user_id") Long userId, @Valid @RequestBody CreatePollRequest createPollRequest,
                                        HttpServletRequest request) {
        String createdPoll = pollService.createPoll(userId, createPollRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(createdPoll)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("{user_id}/active-polls")
    public ResponseEntity<?> activePolls(@PathVariable("user_id") Long userId,
                                        HttpServletRequest request) {
        var activePolls = pollService.activePolls(userId);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .data(activePolls)
                .timestamp(ZonedDateTime.now())
                .path(request.getRequestURI())
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/recent-polls")
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

}
