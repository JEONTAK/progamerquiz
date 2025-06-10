package pq.progamerquiz.domain.quizzes.pieceofpuzzle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pq.progamerquiz.domain.quizzes.pieceofpuzzle.entity.PieceOfPuzzle;

public interface PieceOfPuzzleRepository extends JpaRepository<PieceOfPuzzle, Long> {

    @Modifying
    @Query("UPDATE PieceOfPuzzle pop SET pop.correctQuizCount = :correctQuizCount WHERE pop.id = :id")
    void updateCorrectQuizCount(Long id, Integer correctQuizCount);
}
