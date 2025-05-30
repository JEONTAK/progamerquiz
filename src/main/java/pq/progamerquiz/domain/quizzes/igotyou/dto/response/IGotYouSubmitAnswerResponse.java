package pq.progamerquiz.domain.quizzes.igotyou.dto.response;


import lombok.Getter;

//Quiz : I Got you!
@Getter
public class IGotYouSubmitAnswerResponse {

    private final Long id;
    private final Integer index;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;

    private IGotYouSubmitAnswerResponse(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount) {
        this.id = id;
        this.index = index;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static IGotYouSubmitAnswerResponse of(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount) {
        return new IGotYouSubmitAnswerResponse(id, index, totalQuizCount, correctQuizCount);
    }
}
