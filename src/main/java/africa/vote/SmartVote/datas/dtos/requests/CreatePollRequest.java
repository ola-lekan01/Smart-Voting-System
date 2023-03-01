package africa.vote.SmartVote.datas.dtos.requests;

import africa.vote.SmartVote.datas.enums.Category;
import africa.vote.SmartVote.datas.models.Candidate;
import lombok.Data;

import java.util.List;

@Data
public class CreatePollRequest {
    private String title;
    private String question;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private Category category;
    private List<Candidate> candidates;
}
