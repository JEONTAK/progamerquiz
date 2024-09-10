package pq.progamerquiz.progamer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.team.Team;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Progamer{

    @Id
    @GeneratedValue
    @Column(name = "progamer_id")
    private Long id;

    private String pid;

    private String name;

    private Long birth;

    @Enumerated
    private Position pos;

    @ManyToMany  // 중간 테이블을 활용한 다대다 매핑
    @JoinTable(
            name = "progamer_team",
            joinColumns = @JoinColumn(name = "progamer_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams = new ArrayList<>();

    private Long league_w; // Last Week Wins
    private Long intl_w; // Current Week Wins

    // Enum for Position
    public enum Position {
        TOP, JGL, MID, ADC, SPT
    }
}

