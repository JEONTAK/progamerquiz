package pq.progamerquiz.domain.quizzes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.quizzes.entity.WhichIsTeam;

public interface WhichIsTeamRepository extends JpaRepository<WhichIsTeam, Long> {

    @Modifying
    @Query("UPDATE WhichIsTeam wit SET wit.correctQuizCount = :correctQuizCount WHERE wit.id = :id")
    void updateCorrectQuizCount(@Param("id") Long id, Integer correctQuizCount);
}
