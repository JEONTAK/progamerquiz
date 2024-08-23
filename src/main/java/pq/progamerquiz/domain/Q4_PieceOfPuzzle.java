package pq.progamerquiz.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("Q4")
@Getter
@Setter
public class Q4_PieceOfPuzzle extends Quiz{
}
