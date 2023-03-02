package africa.vote.SmartVote.datas.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    @Column(name="poll_id", nullable = false)
    private List<Poll> polls = new ArrayList<>();
    @ManyToMany
    @Column(name="user_id", nullable = false)
    private List<User> users = new ArrayList<>();
    private boolean voted;
}