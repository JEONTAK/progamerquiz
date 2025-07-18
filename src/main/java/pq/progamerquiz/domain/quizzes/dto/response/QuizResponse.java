package pq.progamerquiz.domain.quizzes.dto.response;

import lombok.Getter;

@Getter
public class QuizResponse {

    private final Long id;
    private final String url;
    private final String imageUrl;
    private final String altText;
    private final String title;
    private final String description;

    private QuizResponse(Long id, String url, String imageUrl, String altText, String title, String description) {
        this.id = id;
        this.url = url;
        this.imageUrl = imageUrl;
        this.altText = altText;
        this.title = title;
        this.description = description;
    }

    public static QuizResponse of(Long id, String url, String imageUrl, String altText, String title, String description) {
        return new QuizResponse(id, url, imageUrl, altText, title, description);
    }
}
