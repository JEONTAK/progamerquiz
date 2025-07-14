package pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WhichIsTeamSubmitAnswerRequest {

    private Long id;
    private Integer index;
    private String input;
    private Integer correctQuizCount;
    private Integer totalQuizCount;

}
