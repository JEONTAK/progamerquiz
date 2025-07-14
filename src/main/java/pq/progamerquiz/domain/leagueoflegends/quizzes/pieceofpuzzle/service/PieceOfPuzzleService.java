package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response.ProgamerSimpleInfoResponse;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.entity.ProgamerLOL;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.service.ProgamerLOLQueryService;
import pq.progamerquiz.domain.leagueoflegends.progamerteamlol.service.ProgamerTeamLOLService;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response.PieceOfPuzzleQuizResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response.PieceOfPuzzleResultResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response.PieceOfPuzzleSubmitAnswerResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.repository.PieceOfPuzzleQuizTeamRepository;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.repository.PieceOfPuzzleRepository;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response.PieceOfPuzzleResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.entity.PieceOfPuzzle;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.entity.PieceOfPuzzleQuizTeam;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;
import pq.progamerquiz.domain.leagueoflegends.teamlol.service.TeamLOLQueryService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PieceOfPuzzleService {

    private final PieceOfPuzzleRepository pieceOfPuzzleRepository;
    private final PieceOfPuzzleQuizTeamRepository pieceOfPuzzleQuizTeamRepository;
    private final TeamLOLQueryService teamLOLQueryService;
    private final ProgamerTeamLOLService progamerTeamLOLService;
    private final ProgamerLOLQueryService progamerLOLQueryService;

    public List<PieceOfPuzzleQuizResponse> setQuizLists(Integer totalQuizCount) {
        PieceOfPuzzle pieceOfPuzzle = PieceOfPuzzle.create(totalQuizCount, 0);
        PieceOfPuzzle savedPieceOfPuzzle = pieceOfPuzzleRepository.save(pieceOfPuzzle);
        List<Long> teamIds = progamerTeamLOLService.findTeamIdsWithFiveOrMoreProgamers();
        List<TeamLOL> teamLOLList = teamLOLQueryService.findRandomTeams(totalQuizCount, teamIds);
        return LongStream.range(0, teamLOLList.size())
                .mapToObj(i -> {
                    TeamLOL teamLOL = teamLOLList.get((int) i);
                    List<ProgamerSimpleInfoResponse> rosters = progamerTeamLOLService.findProgamersByTeamId(teamLOL.getId());
                    List<Long> rosterIds = rosters.stream().map(ProgamerSimpleInfoResponse::getId).toList();
                    ProgamerLOL answerProgamerLOL = progamerLOLQueryService.findOneByIds(rosterIds);
                    rosters = rosters.stream()
                            .filter(progamer -> !progamer.getId().equals(answerProgamerLOL.getId()))
                            .toList();
                    pieceOfPuzzleQuizTeamRepository.save(PieceOfPuzzleQuizTeam.create(savedPieceOfPuzzle, teamLOL, answerProgamerLOL));
                    return PieceOfPuzzleQuizResponse.of(savedPieceOfPuzzle.getId(), i + 1, teamLOL.getName(), teamLOL.getSeasonYear(), teamLOL.getImageId(), answerProgamerLOL.getId(), answerProgamerLOL.getProgamerTag(), answerProgamerLOL.getPosition(), rosters);
                })
                .collect(Collectors.toList());
    }

    public PieceOfPuzzleResponse setQuiz(Long id, List<PieceOfPuzzleQuizResponse> quizList) {
        PieceOfPuzzle pieceOfPuzzle = pieceOfPuzzleRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        return PieceOfPuzzleResponse.of(pieceOfPuzzle.getId(), 0, pieceOfPuzzle.getTotalQuizCount(), pieceOfPuzzle.getCorrectQuizCount(), quizList);
    }

    public PieceOfPuzzleSubmitAnswerResponse submitAnswer(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount, String input) {
        List<PieceOfPuzzleQuizTeam> quizList = pieceOfPuzzleQuizTeamRepository.findByPieceOfPuzzleIdWithProgamer(id);
        ProgamerLOL submitProgamerLOL = progamerLOLQueryService.findByProgamerTag(input);
        if (quizList.get(index).getProgamerLOL().getProgamerTag().equals(submitProgamerLOL.getProgamerTag())) {
            correctQuizCount++;
        }

        return PieceOfPuzzleSubmitAnswerResponse.of(id, index, correctQuizCount, totalQuizCount);
    }

    public PieceOfPuzzleResultResponse saveResult(Long id, Integer correctQuizCount, Integer totalQuizCount) {
        PieceOfPuzzle pieceOfPuzzle = pieceOfPuzzleRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        pieceOfPuzzleRepository.updateCorrectQuizCount(id, correctQuizCount);
        return PieceOfPuzzleResultResponse.of(pieceOfPuzzle.getId(), correctQuizCount, totalQuizCount);
    }

}
