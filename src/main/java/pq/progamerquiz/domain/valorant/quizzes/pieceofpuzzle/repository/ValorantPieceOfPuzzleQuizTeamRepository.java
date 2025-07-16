package pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.entity.ValorantPieceOfPuzzleQuizTeam;

import java.util.List;

public interface ValorantPieceOfPuzzleQuizTeamRepository extends JpaRepository<ValorantPieceOfPuzzleQuizTeam, Long> {

    @Query("SELECT popqt FROM ValorantPieceOfPuzzleQuizTeam popqt JOIN FETCH popqt.progamerValorant WHERE popqt.pieceofpuzzle.id = :pieceOfPuzzleId")
    List<ValorantPieceOfPuzzleQuizTeam> findByPieceOfPuzzleIdWithProgamer(@Param("pieceOfPuzzleId") Long pieceOfPuzzleId);
}
