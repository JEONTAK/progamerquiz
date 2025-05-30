package pq.progamerquiz.domain.quizzes.igotyou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.quizzes.igotyou.entity.IGotYouProgamer;

import java.util.List;

public interface IGotYouProgamerRepository extends JpaRepository<IGotYouProgamer, Long> {

    @Query("SELECT igp FROM IGotYouProgamer igp JOIN FETCH igp.answerProgamer WHERE igp.igotyou.id = :igotyouId")
    List<IGotYouProgamer> findByIgotyouIdWithProgamer(@Param("igotyouId") Long igotyouId);
}
