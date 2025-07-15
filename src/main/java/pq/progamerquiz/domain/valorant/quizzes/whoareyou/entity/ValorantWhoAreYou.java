package pq.progamerquiz.domain.valorant.quizzes.whoareyou.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.common.entity.BaseEntity;
import pq.progamerquiz.domain.valorant.progamervalorant.entity.ProgamerValorant;

@Entity
@Table(name = "whoareyou_valorant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ValorantWhoAreYou extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long attempt;

    @Column(nullable = false)
    private boolean correct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progamer_id", nullable = false)
    private ProgamerValorant quizProgamerValorant;

    private ValorantWhoAreYou(Long attempt, boolean correct, ProgamerValorant quizProgamerValorant) {
        this.attempt = attempt;
        this.correct = correct;
        this.quizProgamerValorant = quizProgamerValorant;
    }

    public static ValorantWhoAreYou create(Long attempt, boolean correct, ProgamerValorant quizProgamerValorant) {
        return new ValorantWhoAreYou(attempt, correct, quizProgamerValorant);
    }

    public void updateResult(Long attempts, boolean correct) {
        this.attempt = attempts;
        this.correct = correct;
    }
}
