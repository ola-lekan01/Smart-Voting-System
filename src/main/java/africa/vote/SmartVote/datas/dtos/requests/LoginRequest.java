package africa.vote.SmartVote.datas.dtos.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginRequest {
    @Column(name = "phone_number", nullable = false, length = 100)
    private String phoneNumber;
    @Column(name="email", nullable = false, length = 100)
    @Email(message = "This field requires a valid email address")
    private String email;
    private String password;
}
