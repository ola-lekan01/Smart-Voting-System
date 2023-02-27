package africa.vote.SmartVote.datas.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OTPtoken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Instant createdTime;
    private Instant expiredTime;
    private Instant confirmedTime;
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="id")
    private Users user;

    public OTPtoken(String token, Instant createdTime, Instant expiredTime,Users user){
        this.token = token;
        this.createdTime = createdTime;
        this.expiredTime = expiredTime;
        this.user = user;
    }
}
