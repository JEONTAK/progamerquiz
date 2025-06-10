package pq.progamerquiz.domain.quizzes.pieceofpuzzle.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerSimpleInfoResponse;
import pq.progamerquiz.domain.progamer.entity.Progamer;
import pq.progamerquiz.domain.progamer.service.ProgamerQueryService;
import pq.progamerquiz.domain.progamerteam.service.ProgamerTeamService;
import pq.progamerquiz.domain.quizzes.pieceofpuzzle.dto.response.PieceOfPuzzleQuizResponse;
import pq.progamerquiz.domain.quizzes.pieceofpuzzle.dto.response.PieceOfPuzzleResponse;
import pq.progamerquiz.domain.quizzes.pieceofpuzzle.dto.response.PieceOfPuzzleResultResponse;
import pq.progamerquiz.domain.quizzes.pieceofpuzzle.dto.response.PieceOfPuzzleSubmitAnswerResponse;
import pq.progamerquiz.domain.quizzes.pieceofpuzzle.entity.PieceOfPuzzle;
import pq.progamerquiz.domain.quizzes.pieceofpuzzle.entity.PieceOfPuzzleQuizTeam;
import pq.progamerquiz.domain.quizzes.pieceofpuzzle.repository.PieceOfPuzzleQuizTeamRepository;
import pq.progamerquiz.domain.quizzes.pieceofpuzzle.repository.PieceOfPuzzleRepository;
import pq.progamerquiz.domain.team.entity.Team;
import pq.progamerquiz.domain.team.service.TeamQueryService;

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
    private final TeamQueryService teamQueryService;
    private final ProgamerTeamService progamerTeamService;
    private final ProgamerQueryService progamerQueryService;

    public List<PieceOfPuzzleQuizResponse> setQuizLists(Integer totalQuizCount) {
        PieceOfPuzzle pieceOfPuzzle = PieceOfPuzzle.create(totalQuizCount, 0);
        PieceOfPuzzle savedPieceOfPuzzle = pieceOfPuzzleRepository.save(pieceOfPuzzle);
        List<Long> teamIds = progamerTeamService.findTeamIdsWithFiveOrMoreProgamers();
        List<Team> teamList = teamQueryService.findRandomTeams(totalQuizCount, teamIds);
        return LongStream.range(0, teamList.size())
                .mapToObj(i -> {
                    Team team = teamList.get((int) i);
                    List<ProgamerSimpleInfoResponse> rosters = progamerTeamService.findProgamersByTeamId(team.getId());
                    List<Long> rosterIds = rosters.stream().map(ProgamerSimpleInfoResponse::getId).toList();
                    Progamer answerProgamer = progamerQueryService.findOneByIds(rosterIds);
                    rosters = rosters.stream()
                            .filter(progamer -> !progamer.getId().equals(answerProgamer.getId()))
                            .toList();
                    pieceOfPuzzleQuizTeamRepository.save(PieceOfPuzzleQuizTeam.create(savedPieceOfPuzzle, team, answerProgamer));
                    return PieceOfPuzzleQuizResponse.of(savedPieceOfPuzzle.getId(), i + 1, team.getName(), team.getSeasonYear(), team.getImageId(), answerProgamer.getId(), answerProgamer.getProgamerTag(), answerProgamer.getPosition(), rosters);
                })
                .collect(Collectors.toList());
    }

    public PieceOfPuzzleResponse setQuiz(Long id, List<PieceOfPuzzleQuizResponse> quizList) {
        PieceOfPuzzle pieceOfPuzzle = pieceOfPuzzleRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        return PieceOfPuzzleResponse.of(pieceOfPuzzle.getId(), 0, pieceOfPuzzle.getTotalQuizCount(), pieceOfPuzzle.getCorrectQuizCount(), quizList);
    }

    public PieceOfPuzzleSubmitAnswerResponse submitAnswer(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount, String input) {
        List<PieceOfPuzzleQuizTeam> quizList = pieceOfPuzzleQuizTeamRepository.findByPieceOfPuzzleIdWithProgamer(id);
        Progamer submitProgamer = progamerQueryService.findByProgamerTag(input);
        if (quizList.get(index).getProgamer().getProgamerTag().equals(submitProgamer.getProgamerTag())) {
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
