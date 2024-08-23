package pq.progamerquiz.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Progamer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progamer_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String pid;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private League league;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Team> teams = new ArrayList<>();

    @Column(nullable = false)
    private Integer birth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Position pos;

    private Integer lw; // Last Week Wins
    private Integer iw; // Current Week Wins
    private String imagePath;

    // Enum for League
    public enum League {
        LCK, LPL, LEC, LCS
    }

    // Enum for Position
    public enum Position {
        TOP, JGL, MID, ADC, SPT
    }


}

