package pq.progamerquiz.domain.quizzes.whichisteam.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.team.entity.Team;

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
    private Team team;

    private WhichIsTeamQuizTeam(WhichIsTeam whichisteam, Team team) {
        this.whichisteam = whichisteam;
        this.team = team;
    }

    public static WhichIsTeamQuizTeam create(WhichIsTeam whichisteam, Team team) {
        return new WhichIsTeamQuizTeam(whichisteam, team);
    }
}
