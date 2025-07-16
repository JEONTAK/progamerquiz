package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pieceofpuzzle_lol")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LOLPieceOfPuzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer totalQuizCount;

    @Column(nullable = false)
    private Integer correctQuizCount;

    private LOLPieceOfPuzzle(Integer totalQuizCount, Integer correctQuizCount) {
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static LOLPieceOfPuzzle create(Integer totalQuizCount, Integer correctQuizCount) {
        return new LOLPieceOfPuzzle(totalQuizCount, correctQuizCount);
    }
}
