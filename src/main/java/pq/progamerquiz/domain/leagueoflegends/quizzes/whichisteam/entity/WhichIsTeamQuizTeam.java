package pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;

@Entity
@Table(name = "whichisteam_team")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WhichIsTeamQuizTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "whichisteam_id", nullable = false)
    private WhichIsTeam whichisteam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamLOL teamLOL;

    private WhichIsTeamQuizTeam(WhichIsTeam whichisteam, TeamLOL teamLOL) {
        this.whichisteam = whichisteam;
        this.teamLOL = teamLOL;
    }

    public static WhichIsTeamQuizTeam create(WhichIsTeam whichisteam, TeamLOL teamLOL) {
        return new WhichIsTeamQuizTeam(whichisteam, teamLOL);
    }
}
