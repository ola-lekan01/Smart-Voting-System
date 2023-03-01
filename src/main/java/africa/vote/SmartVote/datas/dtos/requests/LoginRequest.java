package africa.vote.SmartVote.datas.dtos.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginRequest {
    @NotBlank(message = "This field Cannot be Blank")
    private String email;
    @NotBlank(message = "This field Cannot be Blank")
    private String password;
}
