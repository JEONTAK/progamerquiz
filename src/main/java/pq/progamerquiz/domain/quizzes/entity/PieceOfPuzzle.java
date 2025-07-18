package pq.progamerquiz.domain.quizzes.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.common.enums.Game;

@Entity
@Table(name = "pieceofpuzzle")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PieceOfPuzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer totalQuizCount;

    @Column(nullable = false)
    private Integer correctQuizCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Game game;

    private PieceOfPuzzle(Integer totalQuizCount, Integer correctQuizCount, Game game) {
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
        this.game = game;
    }

    public static PieceOfPuzzle create(Integer totalQuizCount, Integer correctQuizCount, Game game) {
        return new PieceOfPuzzle(totalQuizCount, correctQuizCount, game);
    }
}
