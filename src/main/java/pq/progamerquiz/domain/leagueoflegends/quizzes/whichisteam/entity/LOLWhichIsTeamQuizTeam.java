package pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;
import pq.progamerquiz.domain.quizzes.entity.WhichIsTeam;

@Entity
@Table(name = "whichisteam_team_lol")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LOLWhichIsTeamQuizTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "whichisteam_id", nullable = false)
    private WhichIsTeam whichisteam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamLOL teamLOL;

    private LOLWhichIsTeamQuizTeam(WhichIsTeam whichisteam, TeamLOL teamLOL) {
        this.whichisteam = whichisteam;
        this.teamLOL = teamLOL;
    }

    public static LOLWhichIsTeamQuizTeam create(WhichIsTeam whichisteam, TeamLOL teamLOL) {
        return new LOLWhichIsTeamQuizTeam(whichisteam, teamLOL);
    }
}
