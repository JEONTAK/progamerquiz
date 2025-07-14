package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.entity.ProgamerLOL;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;

@Entity
@Table(name = "pieceofpuzzle_team")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PieceOfPuzzleQuizTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pieceofpuzzle_id", nullable = false)
    private PieceOfPuzzle pieceofpuzzle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamLOL teamLOL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progamer_id", nullable = false)
    private ProgamerLOL progamerLOL;

    private PieceOfPuzzleQuizTeam(PieceOfPuzzle pieceofpuzzle, TeamLOL teamLOL, ProgamerLOL progamerLOL) {
        this.pieceofpuzzle = pieceofpuzzle;
        this.teamLOL = teamLOL;
        this.progamerLOL = progamerLOL;
    }

    public static PieceOfPuzzleQuizTeam create(PieceOfPuzzle pieceOfPuzzle, TeamLOL teamLOL, ProgamerLOL progamerLOL) {
        return new PieceOfPuzzleQuizTeam(pieceOfPuzzle, teamLOL, progamerLOL);
    }
}
