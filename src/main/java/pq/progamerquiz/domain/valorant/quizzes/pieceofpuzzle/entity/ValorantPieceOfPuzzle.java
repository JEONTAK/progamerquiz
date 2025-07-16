package pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pieceofpuzzle_valorant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ValorantPieceOfPuzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer totalQuizCount;

    @Column(nullable = false)
    private Integer correctQuizCount;

    private ValorantPieceOfPuzzle(Integer totalQuizCount, Integer correctQuizCount) {
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static ValorantPieceOfPuzzle create(Integer totalQuizCount, Integer correctQuizCount) {
        return new ValorantPieceOfPuzzle(totalQuizCount, correctQuizCount);
    }
}
