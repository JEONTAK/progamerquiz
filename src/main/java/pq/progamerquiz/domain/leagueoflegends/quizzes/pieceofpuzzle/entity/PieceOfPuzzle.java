package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private PieceOfPuzzle(Integer totalQuizCount, Integer correctQuizCount) {
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static PieceOfPuzzle create(Integer totalQuizCount, Integer correctQuizCount) {
        return new PieceOfPuzzle(totalQuizCount, correctQuizCount);
    }
}
