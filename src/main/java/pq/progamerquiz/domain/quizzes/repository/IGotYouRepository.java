package pq.progamerquiz.domain.quizzes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.quizzes.entity.IGotYou;

public interface IGotYouRepository extends JpaRepository<IGotYou, Long> {

    @Modifying
    @Query("UPDATE IGotYou igy SET igy.correctQuizCount = :correctQuizCount WHERE igy.id = :id")
    void updateCorrectQuizCount(@Param("id") Long id, Integer correctQuizCount);
}
