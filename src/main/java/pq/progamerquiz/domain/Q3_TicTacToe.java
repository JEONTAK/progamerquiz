package pq.progamerquiz.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("Q3")
@Getter
@Setter
public class Q3_TicTacToe extends Quiz{
}
