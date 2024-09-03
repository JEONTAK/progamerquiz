package pq.progamerquiz.quiz;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuizRepository {

    private final EntityManager em;
}
