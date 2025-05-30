package pq.progamerquiz.domain.quizzes.igotyou.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IGotYouSaveResultRequest {

    private Long id;
    private Integer correctQuizCount;
    private Integer totalQuizCount;

}
