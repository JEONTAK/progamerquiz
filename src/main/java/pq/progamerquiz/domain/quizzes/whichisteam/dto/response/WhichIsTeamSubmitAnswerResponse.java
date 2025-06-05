package pq.progamerquiz.domain.quizzes.whichisteam.dto.response;


import lombok.Getter;

//Quiz : I Got you!
@Getter
public class WhichIsTeamSubmitAnswerResponse {

    private final Long id;
    private final Integer index;
    private final Integer correctQuizCount;
    private final Integer totalQuizCount;

    private WhichIsTeamSubmitAnswerResponse(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        this.id = id;
        this.index = index;
        this.correctQuizCount = correctQuizCount;
        this.totalQuizCount = totalQuizCount;
    }

    public static WhichIsTeamSubmitAnswerResponse of(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        return new WhichIsTeamSubmitAnswerResponse(id, index, correctQuizCount, totalQuizCount);
    }
}
