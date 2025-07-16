package pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.common.entity.BaseEntity;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.entity.ProgamerLOL;

@Entity
@Table(name = "whoareyou_lol")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LOLWhoAreYou extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long attempt;

    @Column(nullable = false)
    private boolean correct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "progamer_id", nullable = false)
    private ProgamerLOL quizProgamerLOL;

    private LOLWhoAreYou(Long attempt, boolean correct, ProgamerLOL quizProgamerLOL) {
        this.attempt = attempt;
        this.correct = correct;
        this.quizProgamerLOL = quizProgamerLOL;
    }

    public static LOLWhoAreYou create(Long attempt, boolean correct, ProgamerLOL quizProgamerLOL) {
        return new LOLWhoAreYou(attempt, correct, quizProgamerLOL);
    }

    public void updateResult(Long attempts, boolean correct) {
        this.attempt = attempts;
        this.correct = correct;
    }
}
