package pq.progamerquiz.domain.leagueoflegends.quizzes.whoareyou.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response.ProgamerLOLWithRecentTeamResponse;

import java.util.List;

@Getter
@NoArgsConstructor
public class LOLWhoAreYouSubmitAnswerRequest {

    private String input;
    private Integer attempts;
    private ProgamerLOLWithRecentTeamResponse answer;
    private List<String> guessedList;
}
