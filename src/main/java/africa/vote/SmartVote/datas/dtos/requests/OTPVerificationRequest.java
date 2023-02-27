package africa.vote.SmartVote.datas.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTPVerificationRequest {
    private String token;
    private String email;
}
