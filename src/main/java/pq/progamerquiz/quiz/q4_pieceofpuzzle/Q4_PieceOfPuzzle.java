package pq.progamerquiz.quiz.q4_pieceofpuzzle;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import pq.progamerquiz.quiz.Quiz;

@Entity
@DiscriminatorValue("Q4")
@Getter
@Setter
public class Q4_PieceOfPuzzle extends Quiz {
}
