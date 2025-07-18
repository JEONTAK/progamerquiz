package pq.progamerquiz.domain.quizzes.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.common.enums.Game;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Game game;

    private WhichIsTeam(Integer totalQuizCount, Integer correctQuizCount, Game game) {
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
        this.game = game;
    }

    public static WhichIsTeam create(Integer totalQuizCount, Integer correctQuizCount, Game game) {
        return new WhichIsTeam(totalQuizCount, correctQuizCount, game);
    }
}