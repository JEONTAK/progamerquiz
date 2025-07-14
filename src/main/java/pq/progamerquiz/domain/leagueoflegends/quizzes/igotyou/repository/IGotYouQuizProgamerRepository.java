package pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.entity.IGotYouQuizProgamer;

import java.util.List;

public interface IGotYouQuizProgamerRepository extends JpaRepository<IGotYouQuizProgamer, Long> {

    @Query("SELECT igp FROM IGotYouQuizProgamer igp JOIN FETCH igp.answerProgamerLOL WHERE igp.igotyou.id = :igotyouId")
    List<IGotYouQuizProgamer> findByIgotyouIdWithProgamer(@Param("igotyouId") Long igotyouId);
}
