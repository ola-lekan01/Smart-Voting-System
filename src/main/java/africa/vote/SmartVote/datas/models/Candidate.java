package africa.vote.SmartVote.datas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private String id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="image_url", nullable = false)
    private String imageURL;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "result", referencedColumnName = "id")
    private Result result;
}