package pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response.ProgamerLOLSimpleInfoResponse;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.entity.ProgamerLOL;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.service.ProgamerLOLQueryService;
import pq.progamerquiz.domain.leagueoflegends.progamerteamlol.service.ProgamerTeamLOLService;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response.LOLPieceOfPuzzleQuizResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response.LOLPieceOfPuzzleResultResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response.LOLPieceOfPuzzleSubmitAnswerResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.entity.LOLPieceOfPuzzleQuizTeam;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.repository.LOLPieceOfPuzzleQuizTeamRepository;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.repository.LOLPieceOfPuzzleRepository;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.dto.response.LOLPieceOfPuzzleResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.pieceofpuzzle.entity.LOLPieceOfPuzzle;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;
import pq.progamerquiz.domain.leagueoflegends.teamlol.service.TeamLOLQueryService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class LOLPieceOfPuzzleService {

    private final LOLPieceOfPuzzleRepository lolPieceOfPuzzleRepository;
    private final LOLPieceOfPuzzleQuizTeamRepository lolPieceOfPuzzleQuizTeamRepository;
    private final TeamLOLQueryService teamLOLQueryService;
    private final ProgamerTeamLOLService progamerTeamLOLService;
    private final ProgamerLOLQueryService progamerLOLQueryService;

    public List<LOLPieceOfPuzzleQuizResponse> setQuizLists(Integer totalQuizCount) {
        LOLPieceOfPuzzle lolPieceOfPuzzle = LOLPieceOfPuzzle.create(totalQuizCount, 0);
        LOLPieceOfPuzzle savedLOLPieceOfPuzzle = lolPieceOfPuzzleRepository.save(lolPieceOfPuzzle);
        List<Long> teamIds = progamerTeamLOLService.findTeamIdsWithFiveOrMoreProgamers();
        List<TeamLOL> teamLOLList = teamLOLQueryService.findRandomTeams(totalQuizCount, teamIds);
        return LongStream.range(0, teamLOLList.size())
                .mapToObj(i -> {
                    TeamLOL teamLOL = teamLOLList.get((int) i);
                    List<ProgamerLOLSimpleInfoResponse> rosters = progamerTeamLOLService.findProgamersByTeamId(teamLOL.getId());
                    List<Long> rosterIds = rosters.stream().map(ProgamerLOLSimpleInfoResponse::getId).toList();
                    ProgamerLOL answerProgamerLOL = progamerLOLQueryService.findOneByIds(rosterIds);
                    rosters = rosters.stream()
                            .filter(progamer -> !progamer.getId().equals(answerProgamerLOL.getId()))
                            .toList();
                    lolPieceOfPuzzleQuizTeamRepository.save(LOLPieceOfPuzzleQuizTeam.create(savedLOLPieceOfPuzzle, teamLOL, answerProgamerLOL));
                    return LOLPieceOfPuzzleQuizResponse.of(savedLOLPieceOfPuzzle.getId(), i + 1, teamLOL.getName(), teamLOL.getSeasonYear(), teamLOL.getImageId(), answerProgamerLOL.getId(), answerProgamerLOL.getProgamerTag(), answerProgamerLOL.getPosition(), rosters);
                })
                .collect(Collectors.toList());
    }

    public LOLPieceOfPuzzleResponse setQuiz(Long id, List<LOLPieceOfPuzzleQuizResponse> quizList) {
        LOLPieceOfPuzzle lolPieceOfPuzzle = lolPieceOfPuzzleRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        return LOLPieceOfPuzzleResponse.of(lolPieceOfPuzzle.getId(), 0, lolPieceOfPuzzle.getTotalQuizCount(), lolPieceOfPuzzle.getCorrectQuizCount(), quizList);
    }

    public LOLPieceOfPuzzleSubmitAnswerResponse submitAnswer(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount, String input) {
        List<LOLPieceOfPuzzleQuizTeam> quizList = lolPieceOfPuzzleQuizTeamRepository.findByPieceOfPuzzleIdWithProgamer(id);
        ProgamerLOL submitProgamerLOL = progamerLOLQueryService.findByProgamerTag(input);
        if (quizList.get(index).getProgamerLOL().getProgamerTag().equals(submitProgamerLOL.getProgamerTag())) {
            correctQuizCount++;
        }

        return LOLPieceOfPuzzleSubmitAnswerResponse.of(id, index, correctQuizCount, totalQuizCount);
    }

    public LOLPieceOfPuzzleResultResponse saveResult(Long id, Integer correctQuizCount, Integer totalQuizCount) {
        LOLPieceOfPuzzle lolPieceOfPuzzle = lolPieceOfPuzzleRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        lolPieceOfPuzzleRepository.updateCorrectQuizCount(id, correctQuizCount);
        return LOLPieceOfPuzzleResultResponse.of(lolPieceOfPuzzle.getId(), correctQuizCount, totalQuizCount);
    }

}
