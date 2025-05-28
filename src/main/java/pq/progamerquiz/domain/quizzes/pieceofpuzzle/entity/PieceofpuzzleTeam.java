package pq.progamerquiz.domain.quizzes.pieceofpuzzle.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.progamer.entity.Progamer;
import pq.progamerquiz.domain.team.entity.Team;

@Entity
@Table(name = "pieceofpuzzle_team")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PieceofpuzzleTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pieceofpuzzle_id", nullable = false)
    private Pieceofpuzzle pieceofpuzzle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progamer_id", nullable = false)
    private Progamer progamer;
}
