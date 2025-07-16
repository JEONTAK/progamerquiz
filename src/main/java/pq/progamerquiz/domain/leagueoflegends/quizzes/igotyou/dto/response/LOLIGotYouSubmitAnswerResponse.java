package pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.dto.response;


import lombok.Getter;

@Getter
public class LOLIGotYouSubmitAnswerResponse {

    private final Long id;
    private final Integer index;
    private final Integer correctQuizCount;
    private final Integer totalQuizCount;

    private LOLIGotYouSubmitAnswerResponse(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        this.id = id;
        this.index = index;
        this.correctQuizCount = correctQuizCount;
        this.totalQuizCount = totalQuizCount;
    }

    public static LOLIGotYouSubmitAnswerResponse of(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        return new LOLIGotYouSubmitAnswerResponse(id, index, correctQuizCount, totalQuizCount);
    }
}
