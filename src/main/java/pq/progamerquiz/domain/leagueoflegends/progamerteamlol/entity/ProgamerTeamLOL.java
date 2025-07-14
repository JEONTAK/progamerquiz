package pq.progamerquiz.domain.leagueoflegends.progamerteamlol.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.entity.ProgamerLOL;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;

@Entity
@Table(name = "progamer_team_lol")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgamerTeamLOL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progamer_id", nullable = false)
    private ProgamerLOL progamerLOL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamLOL teamLOL;

    private ProgamerTeamLOL(ProgamerLOL progamerLOL, TeamLOL teamLOL) {
        this.progamerLOL = progamerLOL;
        this.teamLOL = teamLOL;
    }

    public static ProgamerTeamLOL create(ProgamerLOL progamerLOL, TeamLOL teamLOL) {
        return new ProgamerTeamLOL(progamerLOL, teamLOL);
    }
}
