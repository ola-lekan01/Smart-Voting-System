package africa.vote.SmartVote.datas.dtos.responses;

import africa.vote.SmartVote.datas.dtos.requests.CreateCandidateRequest;
import africa.vote.SmartVote.datas.enums.Category;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivePoll {
    private String title;
    private String question;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Category category;
    private List<CandidateResponse> candidates;
}
