package pq.progamerquiz.domain.valorant.quizzes.whichisteam.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.quizzes.entity.WhichIsTeam;
import pq.progamerquiz.domain.valorant.teamvalorant.entity.TeamValorant;

@Entity
@Table(name = "whichisteam_team_valorant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ValorantWhichIsTeamQuizTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "whichisteam_id", nullable = false)
    private WhichIsTeam whichisteam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamValorant teamValorant;

    private ValorantWhichIsTeamQuizTeam(WhichIsTeam whichisteam, TeamValorant teamValorant) {
        this.whichisteam = whichisteam;
        this.teamValorant = teamValorant;
    }

    public static ValorantWhichIsTeamQuizTeam create(WhichIsTeam whichisteam, TeamValorant teamValorant) {
        return new ValorantWhichIsTeamQuizTeam(whichisteam, teamValorant);
    }
}
