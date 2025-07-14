package pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "igotyou")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IGotYou {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer totalQuizCount;

    @Column(nullable = false)
    private Integer correctQuizCount;

    private IGotYou(Integer totalQuizCount, Integer correctQuizCount) {
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static IGotYou create(Integer totalQuizCount, Integer correctQuizCount) {
        return new IGotYou(totalQuizCount, correctQuizCount);
    }
}
