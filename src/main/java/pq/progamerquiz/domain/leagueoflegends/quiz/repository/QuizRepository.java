package pq.progamerquiz.domain.leagueoflegends.quiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pq.progamerquiz.domain.leagueoflegends.quiz.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Quiz findQuizByUrl(String quizUrl);
}
