package africa.vote.SmartVote.datas.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequest {
    private String token;
    private String email;
}
