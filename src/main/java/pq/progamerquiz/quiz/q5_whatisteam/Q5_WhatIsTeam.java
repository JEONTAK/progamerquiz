package pq.progamerquiz.quiz.q5_whatisteam;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import pq.progamerquiz.quiz.Quiz;

@Entity
@DiscriminatorValue("Q5")
@Getter
@Setter
public class Q5_WhatIsTeam extends Quiz {
}
