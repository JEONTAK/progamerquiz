package pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response;


import lombok.Getter;

import java.util.List;

@Getter
public class LOLWhichIsTeamResponse {

    private final Long id;
    private final Integer index;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;
    private final List<LOLWhichIsTeamQuizResponse> quizList;

    private LOLWhichIsTeamResponse(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount, List<LOLWhichIsTeamQuizResponse> quizList) {
        this.id = id;
        this.index = index;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
        this.quizList = quizList;
    }

    public static LOLWhichIsTeamResponse of(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount, List<LOLWhichIsTeamQuizResponse> list) {
        return new LOLWhichIsTeamResponse(id, index, totalQuizCount, correctQuizCount, list);
    }
}
