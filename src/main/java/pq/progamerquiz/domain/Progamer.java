package pq.progamerquiz.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Progamer {

    @Id
    @GeneratedValue
    @Column(name = "progamer_id")
    private Long id;

    private String pid;

    private String name;

    private Long birth;

    @Enumerated(EnumType.STRING)
    private Position position;

    private Long league_win; // Last Week Wins
    private Long intl_win; // Current Week Wins
    private String nationality;

    @ManyToMany  // 중간 테이블을 활용한 다대다 매핑
    @JoinTable(
            name = "progamer_team",
            joinColumns = @JoinColumn(name = "progamer_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams = new ArrayList<>();

    public enum Position {
        TOP, JGL, MID, ADC, SUP,
    }
}

