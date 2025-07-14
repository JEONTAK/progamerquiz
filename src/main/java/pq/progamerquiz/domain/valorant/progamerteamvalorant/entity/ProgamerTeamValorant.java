package pq.progamerquiz.domain.valorant.progamerteamvalorant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.valorant.progamervalorant.entity.ProgamerValorant;
import pq.progamerquiz.domain.valorant.teamvalorant.entity.TeamValorant;

@Entity
@Table(name = "progamer_team_valorant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProgamerTeamValorant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progamer_id", nullable = false)
    private ProgamerValorant progamerValorant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamValorant teamValorant;

    private ProgamerTeamValorant(ProgamerValorant progamerValorant, TeamValorant teamValorant) {
        this.progamerValorant = progamerValorant;
        this.teamValorant = teamValorant;
    }

    public static ProgamerTeamValorant create(ProgamerValorant progamerValorant, TeamValorant teamValorant) {
        return new ProgamerTeamValorant(progamerValorant, teamValorant);
    }
}
