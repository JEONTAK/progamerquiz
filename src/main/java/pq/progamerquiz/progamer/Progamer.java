package pq.progamerquiz.progamer;

import jakarta.persistence.*;
import lombok.Getter;
import java.util.List;

@Entity
@Getter
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

    @OneToMany(mappedBy = "progamer")
    private List<ProgamerTeam> teams;

    private Long league_w; // Last Week Wins
    private Long intl_w; // Current Week Wins

    // Enum for Position
    public enum Position {
        TOP, JGL, MID, ADC, SPT
    }
}

