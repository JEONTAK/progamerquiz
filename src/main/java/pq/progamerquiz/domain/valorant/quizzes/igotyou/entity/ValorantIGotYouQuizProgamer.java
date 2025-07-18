package pq.progamerquiz.domain.valorant.quizzes.igotyou.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.quizzes.entity.IGotYou;
import pq.progamerquiz.domain.valorant.progamervalorant.entity.ProgamerValorant;

@Entity
@Table(name = "igotyou_progamer_valorant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ValorantIGotYouQuizProgamer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "igotyou_id", nullable = false)
    private IGotYou igotyou;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progamer_id", nullable = false)
    private ProgamerValorant answerProgamer;

    private ValorantIGotYouQuizProgamer(IGotYou igotyou, ProgamerValorant answerProgamer) {
        this.igotyou = igotyou;
        this.answerProgamer = answerProgamer;
    }

    public static ValorantIGotYouQuizProgamer create(IGotYou igotyou, ProgamerValorant answerProgamer) {
        return new ValorantIGotYouQuizProgamer(igotyou, answerProgamer);
    }
}
