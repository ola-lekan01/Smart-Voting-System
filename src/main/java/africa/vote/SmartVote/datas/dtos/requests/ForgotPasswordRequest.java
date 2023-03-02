package africa.vote.SmartVote.datas.dtos.requests;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String password;
    private String email;
}
