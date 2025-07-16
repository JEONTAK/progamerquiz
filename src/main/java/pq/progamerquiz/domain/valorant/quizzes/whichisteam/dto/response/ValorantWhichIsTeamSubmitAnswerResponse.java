package pq.progamerquiz.domain.valorant.quizzes.whichisteam.dto.response;


import lombok.Getter;

@Getter
public class ValorantWhichIsTeamSubmitAnswerResponse {

    private final Long id;
    private final Integer index;
    private final Integer correctQuizCount;
    private final Integer totalQuizCount;

    private ValorantWhichIsTeamSubmitAnswerResponse(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        this.id = id;
        this.index = index;
        this.correctQuizCount = correctQuizCount;
        this.totalQuizCount = totalQuizCount;
    }

    public static ValorantWhichIsTeamSubmitAnswerResponse of(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount) {
        return new ValorantWhichIsTeamSubmitAnswerResponse(id, index, correctQuizCount, totalQuizCount);
    }
}
