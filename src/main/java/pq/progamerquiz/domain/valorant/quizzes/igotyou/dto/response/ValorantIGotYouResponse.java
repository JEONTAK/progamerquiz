package pq.progamerquiz.domain.valorant.quizzes.igotyou.dto.response;


import lombok.Getter;

import java.util.List;

@Getter
public class ValorantIGotYouResponse {

    private final Long id;
    private final Integer index;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;
    private final List<ValorantIGotYouQuizResponse> quizList;

    private ValorantIGotYouResponse(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount, List<ValorantIGotYouQuizResponse> quizList) {
        this.id = id;
        this.index = index;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
        this.quizList = quizList;
    }

    public static ValorantIGotYouResponse of(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount, List<ValorantIGotYouQuizResponse> list) {
        return new ValorantIGotYouResponse(id, index, totalQuizCount, correctQuizCount, list);
    }
}
