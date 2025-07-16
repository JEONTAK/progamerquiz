package pq.progamerquiz.domain.quiz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue
    @Column(name = "quiz_id")
    private Long id;
    private String url;
    private String imageUrl;
    private String title;
    private String altText;
    private String description;

    private Quiz(Long id, String url, String imageUrl, String title, String altText, String description) {
        this.id = id;
        this.url = url;
        this.imageUrl = imageUrl;
        this.title = title;
        this.altText = altText;
        this.description = description;
    }

    public static Quiz create(Long id, String url, String imageUrl, String title, String altText, String description) {
        return new Quiz(id, url, imageUrl, title, altText, description);
    }
}
