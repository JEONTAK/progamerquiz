package pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.quizzes.entity.PieceOfPuzzle;
import pq.progamerquiz.domain.valorant.progamervalorant.entity.ProgamerValorant;
import pq.progamerquiz.domain.valorant.teamvalorant.entity.TeamValorant;

@Entity
@Table(name = "pieceofpuzzle_team_valorant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ValorantPieceOfPuzzleQuizTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pieceofpuzzle_id", nullable = false)
    private PieceOfPuzzle pieceofpuzzle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamValorant teamValorant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progamer_id", nullable = false)
    private ProgamerValorant progamerValorant;

    private ValorantPieceOfPuzzleQuizTeam(PieceOfPuzzle pieceofpuzzle, TeamValorant teamValorant, ProgamerValorant progamerValorant) {
        this.pieceofpuzzle = pieceofpuzzle;
        this.teamValorant = teamValorant;
        this.progamerValorant = progamerValorant;
    }

    public static ValorantPieceOfPuzzleQuizTeam create(PieceOfPuzzle pieceOfPuzzle, TeamValorant teamValorant, ProgamerValorant progamerValorant) {
        return new ValorantPieceOfPuzzleQuizTeam(pieceOfPuzzle, teamValorant, progamerValorant);
    }
}
