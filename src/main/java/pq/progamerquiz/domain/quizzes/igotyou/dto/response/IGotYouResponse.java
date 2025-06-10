package pq.progamerquiz.domain.quizzes.igotyou.dto.response;


import lombok.Getter;

import java.util.List;

@Getter
public class IGotYouResponse {

    private final Long id;
    private final Integer index;
    private final Integer totalQuizCount;
    private final Integer correctQuizCount;
    private final List<IGotYouQuizResponse> quizList;

    private IGotYouResponse(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount, List<IGotYouQuizResponse> quizList) {
        this.id = id;
        this.index = index;
        this.totalQuizCount = totalQuizCount;
        this.correctQuizCount = correctQuizCount;
        this.quizList = quizList;
    }

    public static IGotYouResponse of(Long id, Integer index, Integer totalQuizCount, Integer correctQuizCount, List<IGotYouQuizResponse> list) {
        return new IGotYouResponse(id, index, totalQuizCount, correctQuizCount, list);
    }
}
