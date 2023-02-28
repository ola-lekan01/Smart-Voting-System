package africa.vote.SmartVote.datas.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.file.LinkOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime createdTime;
    private LocalDateTime expiredTime;
    private LocalDateTime confirmedTime;
    @OneToOne
    @JoinColumn(name="app_user", referencedColumnName="id")
    private User user;

    public Token(String token, User user){
        this.token = token;
        this.createdTime = LocalDateTime.now();
        this.expiredTime = LocalDateTime.now().plusMinutes(10);
        this.user = user;
    }
}
