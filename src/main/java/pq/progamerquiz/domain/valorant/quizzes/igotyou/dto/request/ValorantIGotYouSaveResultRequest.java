package pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ValorantIGotYouSaveResultRequest {

    private Long id;
    private Integer correctQuizCount;
    private Integer totalQuizCount;

}
