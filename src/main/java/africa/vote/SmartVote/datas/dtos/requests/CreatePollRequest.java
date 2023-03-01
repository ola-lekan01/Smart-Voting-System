package africa.vote.SmartVote.datas.dtos.requests;

import africa.vote.SmartVote.datas.models.Candidate;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CreatePollRequest {
    @NotBlank(message = "This field Cannot be Blank")
    private String title;
    @NotBlank(message = "This field Cannot be Blank")
    private String question;
    @NotBlank(message = "This field Cannot be Blank")
    private String startDate;
    @NotBlank(message = "This field Cannot be Blank")
    private String startTime;
    @NotBlank(message = "This field Cannot be Blank")
    private String endDate;
    @NotBlank(message = "This field Cannot be Blank")
    private String endTime;
    @NotBlank(message = "This field Cannot be Blank")
    private String category;
    @NotBlank(message = "This field Cannot be Blank")
    private List<Candidate> candidates;
}