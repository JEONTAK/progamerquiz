package pq.progamerquiz.domain.quizzes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    private Long id;

    @Column
    private String url;
    @Column
    private String imageUrl;
    @Column
    private String title;
    @Column
    private String altText;
    @Column
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
