package pq.progamerquiz.domain.quiz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizDto {
    private Long id;
    private String url;
    private String imageUrl;
    private String altText;
    private String title;
    private String description;
}
