package africa.vote.SmartVote.controllers;

import africa.vote.SmartVote.datas.dtos.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
@CrossOrigin("*")
@RequestMapping("")
public class SampleController {
    @GetMapping("")
    public ResponseEntity<?> mainPage(HttpServletRequest httpServletRequest)  {

        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .data("Opps That's on Us, Our API's will be available Shortly")
                .timestamp(ZonedDateTime.now())
                .path(httpServletRequest.getRequestURI()+"home")
                .isSuccessful(true)
                .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
}
