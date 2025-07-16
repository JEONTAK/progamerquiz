package pq.progamerquiz.domain.valorant.quizzes.whichisteam.dto.response;


import lombok.Getter;

@Getter
public class ValorantWhichIsTeamResultResponse {

    private final Long id;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;

    private ValorantWhichIsTeamResultResponse(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        this.id = id;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
    }

    public static ValorantWhichIsTeamResultResponse of(Long id, Integer totalQuizCount, Integer correctQuizCount) {
        return new ValorantWhichIsTeamResultResponse(id, totalQuizCount, correctQuizCount);
    }
}
