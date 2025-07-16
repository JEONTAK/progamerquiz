package pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.entity.LOLIGotYouQuizProgamer;

import java.util.List;

public interface LOLIGotYouQuizProgamerRepository extends JpaRepository<LOLIGotYouQuizProgamer, Long> {

    @Query("SELECT igp FROM LOLIGotYouQuizProgamer igp JOIN FETCH igp.answerProgamerLOL WHERE igp.igotyou.id = :igotyouId")
    List<LOLIGotYouQuizProgamer> findByIgotyouIdWithProgamer(@Param("igotyouId") Long igotyouId);
}
