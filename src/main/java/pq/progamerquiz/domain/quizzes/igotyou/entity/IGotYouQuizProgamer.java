package pq.progamerquiz.domain.quizzes.igotyou.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.progamer.entity.Progamer;

@Entity
@Table(name = "igotyou_progamer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IGotYouQuizProgamer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "igotyou_id", nullable = false)
    private IGotYou igotyou;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progamer_id", nullable = false)
    private Progamer answerProgamer;

    private IGotYouQuizProgamer(IGotYou igotyou, Progamer answerProgamer) {
        this.igotyou = igotyou;
        this.answerProgamer = answerProgamer;
    }

    public static IGotYouQuizProgamer create(IGotYou igotyou, Progamer answerProgamer) {
        return new IGotYouQuizProgamer(igotyou, answerProgamer);
    }
}
