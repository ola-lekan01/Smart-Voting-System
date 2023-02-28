package africa.vote.SmartVote.datas.dtos.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    @Column(name = "first_name", nullable = false, length = 100)
    @NotBlank(message = "This Field cannot be blank")
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
    @Column(name = "email", nullable = false, length = 100)
    @Email(message = "This field requires a valid email address")
    @NotBlank(message = "This Field cannot be blank")
    private String email;
    @Column(name = "phone_number", nullable = false, length = 100)
    private String phoneNumber;
    @NotBlank(message = "This Field cannot be blank")
    private String password;
    private String imageUrl;
}