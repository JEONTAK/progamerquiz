package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.entity.LOLPieceOfPuzzle;

public interface LOLPieceOfPuzzleRepository extends JpaRepository<LOLPieceOfPuzzle, Long> {

    @Modifying
    @Query("UPDATE LOLPieceOfPuzzle pop SET pop.correctQuizCount = :correctQuizCount WHERE pop.id = :id")
    void updateCorrectQuizCount(Long id, Integer correctQuizCount);
}
