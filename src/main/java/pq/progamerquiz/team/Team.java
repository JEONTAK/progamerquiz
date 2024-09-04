package pq.progamerquiz.team;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import pq.progamerquiz.progamer.Progamer;


@Entity
@Getter

public class Team{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String name;

    private Long year;

    @Enumerated(EnumType.STRING)
    private League league;

    private Long leagueRank;

    private Long msiRank;

    private Long worldsRank;

    @ManyToMany(mappedBy = "teams")
    private List<Progamer> roster = new ArrayList<>();

    public enum League {
        LCK, LPL, LEC, LCS
    }
}
