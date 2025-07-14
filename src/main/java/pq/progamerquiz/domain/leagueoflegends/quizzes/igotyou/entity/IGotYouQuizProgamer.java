package pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.entity.ProgamerLOL;

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
    private ProgamerLOL answerProgamerLOL;

    private IGotYouQuizProgamer(IGotYou igotyou, ProgamerLOL answerProgamerLOL) {
        this.igotyou = igotyou;
        this.answerProgamerLOL = answerProgamerLOL;
    }

    public static IGotYouQuizProgamer create(IGotYou igotyou, ProgamerLOL answerProgamerLOL) {
        return new IGotYouQuizProgamer(igotyou, answerProgamerLOL);
    }
}
