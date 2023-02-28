package africa.vote.SmartVote.datas.models;

import africa.vote.SmartVote.datas.enums.Category;
import africa.vote.SmartVote.datas.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="first_name", nullable = false, length = 100)
    private String firstName;
    @Column(name="lastName_name", nullable = false, length = 100)
    private String lastName;
    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;
    @Column(name="email", nullable = false, length = 100)
    @Email(message = "This field requires a valid email address")
    private String email;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String imageUrl;
    @JsonIgnore
    private String password;
}
