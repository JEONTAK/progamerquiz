package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.entity.ProgamerLOL;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;

@Entity
@Table(name = "pieceofpuzzle_team_lol")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LOLPieceOfPuzzleQuizTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pieceofpuzzle_id", nullable = false)
    private LOLPieceOfPuzzle pieceofpuzzle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamLOL teamLOL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progamer_id", nullable = false)
    private ProgamerLOL progamerLOL;

    private LOLPieceOfPuzzleQuizTeam(LOLPieceOfPuzzle pieceofpuzzle, TeamLOL teamLOL, ProgamerLOL progamerLOL) {
        this.pieceofpuzzle = pieceofpuzzle;
        this.teamLOL = teamLOL;
        this.progamerLOL = progamerLOL;
    }

    public static LOLPieceOfPuzzleQuizTeam create(LOLPieceOfPuzzle lolPieceOfPuzzle, TeamLOL teamLOL, ProgamerLOL progamerLOL) {
        return new LOLPieceOfPuzzleQuizTeam(lolPieceOfPuzzle, teamLOL, progamerLOL);
    }
}
