package pq.progamerquiz.domain.quizzes.whichisteam.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "whichisteam")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WhichIsTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer totalQuizCount;

    @Column(nullable = false)
    private Integer correctQuizCount;

    private WhichIsTeam(Integer totalQuizCount, Integer correctQuizCount) {
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static WhichIsTeam create(Integer totalQuizCount, Integer correctQuizCount) {
        return new WhichIsTeam(totalQuizCount, correctQuizCount);
    }
}
