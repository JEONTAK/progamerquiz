package pq.progamerquiz.domain.valorant.quizzes.whichisteam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.valorant.quizzes.whichisteam.entity.ValorantWhichIsTeam;

public interface ValorantWhichIsTeamRepository extends JpaRepository<ValorantWhichIsTeam, Long> {

    @Modifying
    @Query("UPDATE ValorantWhichIsTeam wit SET wit.correctQuizCount = :correctQuizCount WHERE wit.id = :id")
    void updateCorrectQuizCount(@Param("id") Long id, Integer correctQuizCount);
}
