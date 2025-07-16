package pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "igotyou_lol")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LOLIGotYou {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer totalQuizCount;

    @Column(nullable = false)
    private Integer correctQuizCount;

    private LOLIGotYou(Integer totalQuizCount, Integer correctQuizCount) {
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static LOLIGotYou create(Integer totalQuizCount, Integer correctQuizCount) {
        return new LOLIGotYou(totalQuizCount, correctQuizCount);
    }
}
