package pq.progamerquiz.team;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;


@Entity
@Getter

public class Team{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private League league;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<TeamInfo> teamInfos;

    public enum League {
        LCK, LPL, LEC, LCS
    }
}
