package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.entity.LOLPieceOfPuzzleQuizTeam;

import java.util.List;

public interface LOLPieceOfPuzzleQuizTeamRepository extends JpaRepository<LOLPieceOfPuzzleQuizTeam, Long> {

    @Query("SELECT popqt FROM LOLPieceOfPuzzleQuizTeam popqt JOIN FETCH popqt.progamerLOL WHERE popqt.pieceofpuzzle.id = :pieceOfPuzzleId")
    List<LOLPieceOfPuzzleQuizTeam> findByPieceOfPuzzleIdWithProgamer(@Param("pieceOfPuzzleId") Long pieceOfPuzzleId);
}
