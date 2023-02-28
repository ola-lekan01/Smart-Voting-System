package africa.vote.SmartVote.datas.dtos.requests;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ResendTokenRequest {
    private String email;
}
