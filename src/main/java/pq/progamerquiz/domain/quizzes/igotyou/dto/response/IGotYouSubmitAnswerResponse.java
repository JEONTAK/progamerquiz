package pq.progamerquiz.domain.quizzes.igotyou.dto.response;


import lombok.Getter;

@Getter
public class IGotYouSubmitAnswerResponse {

    private final Long id;
    private final Integer index;
    private final Integer correctQuizCount;
    private final Integer totalQuizCount;

    private IGotYouSubmitAnswerResponse(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        this.id = id;
        this.index = index;
        this.correctQuizCount = correctQuizCount;
        this.totalQuizCount = totalQuizCount;
    }

    public static IGotYouSubmitAnswerResponse of(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        return new IGotYouSubmitAnswerResponse(id, index, correctQuizCount, totalQuizCount);
    }
}
