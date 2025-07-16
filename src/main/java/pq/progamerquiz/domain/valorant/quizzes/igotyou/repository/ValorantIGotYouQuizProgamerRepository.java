package pq.progamerquiz.domain.valorant.quizzes.igotyou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.entity.ValorantIGotYouQuizProgamer;

import java.util.List;

public interface ValorantIGotYouQuizProgamerRepository extends JpaRepository<ValorantIGotYouQuizProgamer, Long> {

    @Query("SELECT igp FROM ValorantIGotYouQuizProgamer igp JOIN FETCH igp.answerProgamer WHERE igp.igotyou.id = :igotyouId")
    List<ValorantIGotYouQuizProgamer> findByIgotyouIdWithProgamer(@Param("igotyouId") Long igotyouId);
}
