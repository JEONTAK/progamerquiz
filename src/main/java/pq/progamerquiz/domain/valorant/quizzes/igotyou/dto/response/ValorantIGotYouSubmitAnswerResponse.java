package pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.response;


import lombok.Getter;

@Getter
public class ValorantIGotYouSubmitAnswerResponse {

    private final Long id;
    private final Integer index;
    private final Integer correctQuizCount;
    private final Integer totalQuizCount;

    private ValorantIGotYouSubmitAnswerResponse(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        this.id = id;
        this.index = index;
        this.correctQuizCount = correctQuizCount;
        this.totalQuizCount = totalQuizCount;
    }

    public static ValorantIGotYouSubmitAnswerResponse of(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        return new ValorantIGotYouSubmitAnswerResponse(id, index, correctQuizCount, totalQuizCount);
    }
}
