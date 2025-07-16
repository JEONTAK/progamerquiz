package pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValorantIGotYouSubmitAnswerRequest {

    private Long id;
    private Integer index;
    private String input;
    private Integer correctQuizCount;
    private Integer totalQuizCount;

}
