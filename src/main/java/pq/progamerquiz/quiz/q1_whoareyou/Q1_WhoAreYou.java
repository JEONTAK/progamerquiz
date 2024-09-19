package pq.progamerquiz.quiz.q1_whoareyou;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import pq.progamerquiz.progamer.Progamer;
import pq.progamerquiz.quiz.Quiz;

import java.util.List;

@Getter
@Setter
public class Q1_WhoAreYou extends Quiz {

    private Progamer answer;
    private List<Progamer> attemptList;
}

