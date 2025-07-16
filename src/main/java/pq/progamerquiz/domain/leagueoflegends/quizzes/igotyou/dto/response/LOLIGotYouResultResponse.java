package pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response;


import lombok.Getter;

@Getter
public class LOLIGotYouResultResponse {

    private final Long id;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;

    private LOLIGotYouResultResponse(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        this.id = id;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static LOLIGotYouResultResponse of(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        return new LOLIGotYouResultResponse(id, totalQuizCount, correctQuizCount);
    }
}
