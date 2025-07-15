package pq.progamerquiz.domain.valorant.quizzes.whoareyou.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.valorant.progamervalorant.dto.response.ProgamerValorantWithRecentTeamResponse;

import java.util.List;

@Getter
@NoArgsConstructor
public class ValorantWhoAreYouSubmitAnswerRequest {

    private String input;
    private Integer attempts;
    private ProgamerValorantWithRecentTeamResponse answer;
    private List<String> guessedList;
}
