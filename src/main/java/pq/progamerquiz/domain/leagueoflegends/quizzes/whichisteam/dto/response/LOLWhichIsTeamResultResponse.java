package pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response;


import lombok.Getter;

@Getter
public class LOLWhichIsTeamResultResponse {

    private final Long id;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;

    private LOLWhichIsTeamResultResponse(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        this.id = id;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static LOLWhichIsTeamResultResponse of(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        return new LOLWhichIsTeamResultResponse(id, totalQuizCount, correctQuizCount);
    }
}
