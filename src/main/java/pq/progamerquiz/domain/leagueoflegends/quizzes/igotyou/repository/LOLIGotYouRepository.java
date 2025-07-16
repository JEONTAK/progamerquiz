package pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.leagueoflegends.quizzes.igotyou.entity.LOLIGotYou;

public interface LOLIGotYouRepository extends JpaRepository<LOLIGotYou, Long> {

    @Modifying
    @Query("UPDATE LOLIGotYou igy SET igy.correctQuizCount = :correctQuizCount WHERE igy.id = :id")
    void updateCorrectQuizCount(@Param("id") Long id, Integer correctQuizCount);
}
