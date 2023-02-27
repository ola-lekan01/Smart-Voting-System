package africa.vote.SmartVote.datas.dtos.requests;

import africa.vote.SmartVote.datas.enums.Category;
import africa.vote.SmartVote.datas.enums.Status;
import jakarta.mail.Message;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
    @Column(name = "email", nullable = false, length = 100)
    @Email(message = "This field requires a valid email address")
    private String email;
    @Column(name = "phone_number", nullable = false, length = 100)
    private String phoneNumber;
    private String password;
    @Enumerated(EnumType.ORDINAL)
    private Category category;
    private String imageUrl;
}
