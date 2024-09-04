package pq.progamerquiz.progamer;

import jakarta.persistence.*;
import java.util.ArrayList;
import lombok.Getter;
import java.util.List;
import pq.progamerquiz.team.Team;

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

    @Enumerated(EnumType.STRING)
    private Position pos;

    private Long league_w; //League Wins
    private Long intl_w; //Intl Wins

    @ManyToMany
    @JoinTable(
            name = "progamer_team",
            joinColumns = @JoinColumn(name = "progamer_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams = new ArrayList<>();


    // Enum for Position
    public enum Position {
        TOP, JGL, MID, ADC, SPT
    }
}

