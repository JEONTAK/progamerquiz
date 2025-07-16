package pq.progamerquiz.domain.valorant.quizzes.whichisteam.dto.response;


import lombok.Getter;

import java.util.List;

@Getter
public class ValorantWhichIsTeamResponse {

    private final Long id;
    private final Integer index;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;
    private final List<ValorantWhichIsTeamQuizResponse> quizList;

    private ValorantWhichIsTeamResponse(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount, List<ValorantWhichIsTeamQuizResponse> quizList) {
        this.id = id;
        this.index = index;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
        this.quizList = quizList;
    }

    public static ValorantWhichIsTeamResponse of(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount, List<ValorantWhichIsTeamQuizResponse> list) {
        return new ValorantWhichIsTeamResponse(id, index, totalQuizCount, correctQuizCount, list);
    }
}
