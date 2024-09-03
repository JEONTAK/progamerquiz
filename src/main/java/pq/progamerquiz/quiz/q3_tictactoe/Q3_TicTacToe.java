package pq.progamerquiz.quiz.q3_tictactoe;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import pq.progamerquiz.quiz.Quiz;

@Entity
@DiscriminatorValue("Q3")
@Getter
@Setter
public class Q3_TicTacToe extends Quiz {
}
