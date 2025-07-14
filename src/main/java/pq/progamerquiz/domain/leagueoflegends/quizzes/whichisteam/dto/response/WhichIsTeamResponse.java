package pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response;


import lombok.Getter;

import java.util.List;

@Getter
public class WhichIsTeamResponse {

    private final Long id;
    private final Integer index;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;
    private final List<WhichIsTeamQuizResponse> quizList;

    private WhichIsTeamResponse(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount, List<WhichIsTeamQuizResponse> quizList) {
        this.id = id;
        this.index = index;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
        this.quizList = quizList;
    }

    public static WhichIsTeamResponse of(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount, List<WhichIsTeamQuizResponse> list) {
        return new WhichIsTeamResponse(id, index, totalQuizCount, correctQuizCount, list);
    }
}
