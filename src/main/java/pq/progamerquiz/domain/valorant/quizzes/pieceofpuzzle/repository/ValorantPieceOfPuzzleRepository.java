package pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.entity.ValorantPieceOfPuzzle;

public interface ValorantPieceOfPuzzleRepository extends JpaRepository<ValorantPieceOfPuzzle, Long> {

    @Modifying
    @Query("UPDATE ValorantPieceOfPuzzle pop SET pop.correctQuizCount = :correctQuizCount WHERE pop.id = :id")
    void updateCorrectQuizCount(Long id, Integer correctQuizCount);
}
