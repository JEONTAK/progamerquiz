package pq.progamerquiz.domain.quizzes.whoareyou.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WhoAreYouRequest {

    private Long id;
    private Long attempts;
    @JsonProperty("isCorrect")
    private boolean isCorrect;

}
