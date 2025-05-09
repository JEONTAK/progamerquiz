package pq.progamerquiz.domain.progamerteam.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.progamer.entity.Progamer;
import pq.progamerquiz.domain.team.entity.Team;

@Entity
@Table(name = "progamer_team")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgamerTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progamer_id", nullable = false)
    private Progamer progamer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    private ProgamerTeam(Progamer progamer, Team team) {
        this.progamer = progamer;
        this.team = team;
    }

    public static ProgamerTeam create(Progamer progamer, Team team) {
        return new ProgamerTeam(progamer, team);
    }
}
