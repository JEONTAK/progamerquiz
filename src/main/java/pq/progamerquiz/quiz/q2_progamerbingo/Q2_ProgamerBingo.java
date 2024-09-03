package pq.progamerquiz.quiz.q2_progamerbingo;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import pq.progamerquiz.quiz.Quiz;

@Entity
@DiscriminatorValue("Q2")
@Getter
@Setter
public class Q2_ProgamerBingo extends Quiz {
}
