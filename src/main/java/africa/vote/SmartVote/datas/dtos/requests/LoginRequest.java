package africa.vote.SmartVote.datas.dtos.requests;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequest {
    private String email;
    private String password;
}
