package africa.vote.SmartVote.datas.dtos.requests;

import africa.vote.SmartVote.datas.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Enumerated(EnumType.STRING)
    private Category category;
    private String imageUrl;
}
