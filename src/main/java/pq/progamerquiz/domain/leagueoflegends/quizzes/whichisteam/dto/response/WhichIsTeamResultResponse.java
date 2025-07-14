package pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response;


import lombok.Getter;

@Getter
public class WhichIsTeamResultResponse {

    private final Long id;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;

    private WhichIsTeamResultResponse(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        this.id = id;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static WhichIsTeamResultResponse of(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        return new WhichIsTeamResultResponse(id, totalQuizCount, correctQuizCount);
    }
}
