package pq.progamerquiz.domain.valorant.quizzes.igotyou.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "igotyou_valorant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ValorantIGotYou {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer totalQuizCount;

    @Column(nullable = false)
    private Integer correctQuizCount;

    private ValorantIGotYou(Integer totalQuizCount, Integer correctQuizCount) {
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static ValorantIGotYou create(Integer totalQuizCount, Integer correctQuizCount) {
        return new ValorantIGotYou(totalQuizCount, correctQuizCount);
    }
}
