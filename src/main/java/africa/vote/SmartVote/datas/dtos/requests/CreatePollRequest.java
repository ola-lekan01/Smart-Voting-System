package africa.vote.SmartVote.datas.dtos.requests;

import africa.vote.SmartVote.datas.models.Candidate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreatePollRequest {
    @NotBlank(message = "This field Cannot be Blank")
    private String title;
    @NotBlank(message = "This field Cannot be Blank")
    private String question;
    @NotBlank(message = "This field Cannot be Blank")
    private String startDateTime;
//    @NotBlank(message = "This field Cannot be Blank")
//    private String startTime;
    @NotBlank(message = "This field Cannot be Blank")
    private String endDateTime;
//    @NotBlank(message = "This field Cannot be Blank")
//    private String endTime;
    @NotBlank(message = "This field Cannot be Blank")
    private String category;
    @NotEmpty(message = "This field Cannot be Blank")
    private List<Candidate> candidates;
}