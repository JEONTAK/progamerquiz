package pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response.ProgamerWithRecentTeamResponse;

import java.util.List;

@Getter
@NoArgsConstructor
public class WhoAreYouSubmitAnswerRequest {

    private String input;
    private Integer attempts;
    private ProgamerWithRecentTeamResponse answer;
    private List<String> guessedList;
}
