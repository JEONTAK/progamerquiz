package pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.valorant.progamerteamvalorant.service.ProgamerTeamValorantService;
import pq.progamerquiz.domain.valorant.progamervalorant.dto.response.ProgamerValorantSimpleInfoResponse;
import pq.progamerquiz.domain.valorant.progamervalorant.entity.ProgamerValorant;
import pq.progamerquiz.domain.valorant.progamervalorant.service.ProgamerValorantQueryService;
import pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.dto.response.ValorantPieceOfPuzzleQuizResponse;
import pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.dto.response.ValorantPieceOfPuzzleResponse;
import pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.dto.response.ValorantPieceOfPuzzleResultResponse;
import pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.dto.response.ValorantPieceOfPuzzleSubmitAnswerResponse;
import pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.entity.ValorantPieceOfPuzzle;
import pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.entity.ValorantPieceOfPuzzleQuizTeam;
import pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.repository.ValorantPieceOfPuzzleQuizTeamRepository;
import pq.progamerquiz.domain.valorant.quizzes.pieceofpuzzle.repository.ValorantPieceOfPuzzleRepository;
import pq.progamerquiz.domain.valorant.teamvalorant.entity.TeamValorant;
import pq.progamerquiz.domain.valorant.teamvalorant.service.TeamValorantQueryService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ValorantPieceOfPuzzleService {

    private final ValorantPieceOfPuzzleRepository valorantPieceOfPuzzleRepository;
    private final ValorantPieceOfPuzzleQuizTeamRepository valorantPieceOfPuzzleQuizTeamRepository;
    private final TeamValorantQueryService teamValorantQueryService;
    private final ProgamerTeamValorantService progamerTeamValorantService;
    private final ProgamerValorantQueryService progamerValorantQueryService;

    public List<ValorantPieceOfPuzzleQuizResponse> setQuizLists(Integer totalQuizCount) {
        ValorantPieceOfPuzzle pieceOfPuzzle = ValorantPieceOfPuzzle.create(totalQuizCount, 0);
        ValorantPieceOfPuzzle savedPieceOfPuzzle = valorantPieceOfPuzzleRepository.save(pieceOfPuzzle);
        List<Long> teamIds = progamerTeamValorantService.findTeamIdsWithFiveOrMoreProgamers();
        List<TeamValorant> teamList = teamValorantQueryService.findRandomTeams(totalQuizCount, teamIds);
        return LongStream.range(0, teamList.size())
                .mapToObj(i -> {
                    TeamValorant team = teamList.get((int) i);
                    List<ProgamerValorantSimpleInfoResponse> rosters = progamerTeamValorantService.findProgamersByTeamId(team.getId());
                    List<Long> rosterIds = rosters.stream().map(ProgamerValorantSimpleInfoResponse::getId).toList();
                    ProgamerValorant answerProgamer = progamerValorantQueryService.findOneByIds(rosterIds);
                    rosters = rosters.stream()
                            .filter(progamer -> !progamer.getId().equals(answerProgamer.getId()))
                            .toList();
                    valorantPieceOfPuzzleQuizTeamRepository.save(ValorantPieceOfPuzzleQuizTeam.create(savedPieceOfPuzzle, team, answerProgamer));
                    return ValorantPieceOfPuzzleQuizResponse.of(savedPieceOfPuzzle.getId(), i + 1, team.getName(), team.getCallName(), team.getSeasonYear(), answerProgamer.getId(), answerProgamer.getProgamerTag(), answerProgamer.getNationality(), rosters);
                })
                .collect(Collectors.toList());
    }

    public ValorantPieceOfPuzzleResponse setQuiz(Long id, List<ValorantPieceOfPuzzleQuizResponse> quizList) {
        ValorantPieceOfPuzzle pieceOfPuzzle = valorantPieceOfPuzzleRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        return ValorantPieceOfPuzzleResponse.of(pieceOfPuzzle.getId(), 0, pieceOfPuzzle.getTotalQuizCount(), pieceOfPuzzle.getCorrectQuizCount(), quizList);
    }

    public ValorantPieceOfPuzzleSubmitAnswerResponse submitAnswer(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount, String input) {
        List<ValorantPieceOfPuzzleQuizTeam> quizList = valorantPieceOfPuzzleQuizTeamRepository.findByPieceOfPuzzleIdWithProgamer(id);
        ProgamerValorant submitProgamer = progamerValorantQueryService.findByProgamerTag(input);
        if (quizList.get(index).getProgamerValorant().getProgamerTag().equals(submitProgamer.getProgamerTag())) {
            correctQuizCount++;
        }

        return ValorantPieceOfPuzzleSubmitAnswerResponse.of(id, index, correctQuizCount, totalQuizCount);
    }

    public ValorantPieceOfPuzzleResultResponse saveResult(Long id, Integer correctQuizCount, Integer totalQuizCount) {
        ValorantPieceOfPuzzle pieceOfPuzzle = valorantPieceOfPuzzleRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        valorantPieceOfPuzzleRepository.updateCorrectQuizCount(id, correctQuizCount);
        return ValorantPieceOfPuzzleResultResponse.of(pieceOfPuzzle.getId(), correctQuizCount, totalQuizCount);
    }

}
