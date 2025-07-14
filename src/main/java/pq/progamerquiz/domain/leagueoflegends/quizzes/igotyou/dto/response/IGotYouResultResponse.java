package pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response;


import lombok.Getter;

@Getter
public class IGotYouResultResponse {

    private final Long id;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;

    private IGotYouResultResponse(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        this.id = id;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static IGotYouResultResponse of(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        return new IGotYouResultResponse(id, totalQuizCount, correctQuizCount);
    }
}
