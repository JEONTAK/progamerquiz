package pq.progamerquiz.domain.quizzes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pq.progamerquiz.domain.quizzes.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
