package pq.progamerquiz.team;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pq.progamerquiz.progamer.Progamer;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Team{

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    private String callName;

    private Long seasonYear;

    @Enumerated(EnumType.STRING)
    private League league;

    private Long spring_rank;

    private Long summer_rank;

    private Long msi_rank;

    private Long worlds_rank;

    private Long winter_rank;

    @ManyToMany(mappedBy = "teams")
    private List<Progamer> roster = new ArrayList<>();

    public enum League {
        LCK, LPL, LEC, LCS, CBLOL, PCS, LLA, VCS, LJL, LCO, LMS, TCL, LST
    }
}
