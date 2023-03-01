package africa.vote.SmartVote.datas.models;

import africa.vote.SmartVote.datas.enums.Category;
import africa.vote.SmartVote.datas.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="title", nullable = false, length = 255)
    private String title;
    @Column(name="question", nullable = false, length = 255)
    private String question;
    @Column(name="start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;
    @Column(name="end_date", nullable = false)
    private LocalDate endDate;
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;
    @JoinColumn(name = "users", referencedColumnName = "id")
    @ManyToOne
    private Users users;
    @Enumerated(EnumType.ORDINAL)
    private Category category;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "poll_id", referencedColumnName = "id")
    private List<Candidate>candidates;
}
