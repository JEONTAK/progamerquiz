package pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WhichIsTeamSaveResultRequest {

    private Long id;
    private Integer correctQuizCount;
    private Integer totalQuizCount;

}
