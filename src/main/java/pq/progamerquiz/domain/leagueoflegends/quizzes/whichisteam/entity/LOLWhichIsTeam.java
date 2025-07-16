package pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "whichisteam_lol")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LOLWhichIsTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer totalQuizCount;

    @Column(nullable = false)
    private Integer correctQuizCount;

    private LOLWhichIsTeam(Integer totalQuizCount, Integer correctQuizCount) {
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static LOLWhichIsTeam create(Integer totalQuizCount, Integer correctQuizCount) {
        return new LOLWhichIsTeam(totalQuizCount, correctQuizCount);
    }
}
