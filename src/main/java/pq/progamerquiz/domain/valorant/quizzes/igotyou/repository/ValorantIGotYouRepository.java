package pq.progamerquiz.domain.valorant.quizzes.igotyou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.valorant.quizzes.igotyou.entity.ValorantIGotYou;

public interface ValorantIGotYouRepository extends JpaRepository<ValorantIGotYou, Long> {

    @Modifying
    @Query("UPDATE ValorantIGotYou igy SET igy.correctQuizCount = :correctQuizCount WHERE igy.id = :id")
    void updateCorrectQuizCount(@Param("id") Long id, Integer correctQuizCount);
}
