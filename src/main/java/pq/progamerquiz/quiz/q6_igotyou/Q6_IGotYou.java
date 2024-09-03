package pq.progamerquiz.quiz.q6_igotyou;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import pq.progamerquiz.quiz.Quiz;

@Entity
@DiscriminatorValue("Q6")
@Getter
@Setter
public class Q6_IGotYou extends Quiz {
}
