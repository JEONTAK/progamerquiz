package pq.progamerquiz.domain.quizzes.pieceofpuzzle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.quizzes.pieceofpuzzle.entity.PieceOfPuzzleQuizTeam;

import java.util.List;

public interface PieceOfPuzzleQuizTeamRepository extends JpaRepository<PieceOfPuzzleQuizTeam, Long> {

    @Query("SELECT popqt FROM PieceOfPuzzleQuizTeam popqt JOIN FETCH popqt.progamer WHERE popqt.pieceofpuzzle.id = :pieceOfPuzzleId")
    List<PieceOfPuzzleQuizTeam> findByPieceOfPuzzleIdWithProgamer(@Param("pieceOfPuzzleId") Long pieceOfPuzzleId);
}
