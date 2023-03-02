package africa.vote.SmartVote.datas.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable = false, length = 255)
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "result", referencedColumnName = "id")
    private Result result;
}