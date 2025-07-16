package pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response;


import lombok.Getter;

@Getter
public class LOLWhichIsTeamSubmitAnswerResponse {

    private final Long id;
    private final Integer index;
    private final Integer correctQuizCount;
    private final Integer totalQuizCount;

    private LOLWhichIsTeamSubmitAnswerResponse(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        this.id = id;
        this.index = index;
        this.correctQuizCount = correctQuizCount;
        this.totalQuizCount = totalQuizCount;
    }

    public static LOLWhichIsTeamSubmitAnswerResponse of(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        return new LOLWhichIsTeamSubmitAnswerResponse(id, index, correctQuizCount, totalQuizCount);
    }
}
