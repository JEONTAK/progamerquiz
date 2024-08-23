package pq.progamerquiz.domain;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Setter
@Getter
public class Quiz {

    @Id
    @GeneratedValue
    @Column(name = "quiz_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private QuizType quizType;

    private String url;
    private String imageUrl;
    private String altText;
    private String title;
    private String description;

    // 기본 생성자
    public Quiz() {
    }

    // 모든 필드를 사용하는 생성자
    public Quiz(String url, String imageUrl, String title, String description) {
        this.url = url;
        this.imageUrl = imageUrl;
        this.altText = title;
        this.title = title;
        this.description = description;
    }

}
