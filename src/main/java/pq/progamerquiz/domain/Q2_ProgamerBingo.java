package pq.progamerquiz.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("Q2")
@Getter
@Setter
public class Q2_ProgamerBingo extends Quiz{
}
