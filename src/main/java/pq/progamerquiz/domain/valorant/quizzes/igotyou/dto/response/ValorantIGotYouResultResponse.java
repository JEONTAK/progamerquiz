package pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.response;


import lombok.Getter;

@Getter
public class ValorantIGotYouResultResponse {

    private final Long id;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;

    private ValorantIGotYouResultResponse(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        this.id = id;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static ValorantIGotYouResultResponse of(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        return new ValorantIGotYouResultResponse(id, totalQuizCount, correctQuizCount);
    }
}
