package pq.progamerquiz.domain.valorant.quizzes.whichisteam.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.enums.Game;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.quizzes.entity.WhichIsTeam;
import pq.progamerquiz.domain.quizzes.repository.WhichIsTeamRepository;
import pq.progamerquiz.domain.valorant.progamerteamvalorant.service.ProgamerTeamValorantService;
import pq.progamerquiz.domain.valorant.progamervalorant.dto.response.ProgamerValorantSimpleInfoResponse;
import pq.progamerquiz.domain.valorant.quizzes.whichisteam.dto.response.ValorantWhichIsTeamQuizResponse;
import pq.progamerquiz.domain.valorant.quizzes.whichisteam.dto.response.ValorantWhichIsTeamResponse;
import pq.progamerquiz.domain.valorant.quizzes.whichisteam.dto.response.ValorantWhichIsTeamResultResponse;
import pq.progamerquiz.domain.valorant.quizzes.whichisteam.dto.response.ValorantWhichIsTeamSubmitAnswerResponse;
import pq.progamerquiz.domain.valorant.quizzes.whichisteam.entity.ValorantWhichIsTeamQuizTeam;
import pq.progamerquiz.domain.valorant.quizzes.whichisteam.repository.ValorantWhichIsTeamQuizTeamRepository;
import pq.progamerquiz.domain.valorant.teamvalorant.entity.TeamValorant;
import pq.progamerquiz.domain.valorant.teamvalorant.service.TeamValorantQueryService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ValorantWhichIsTeamService {

    private final WhichIsTeamRepository whichIsTeamRepository;
    private final ValorantWhichIsTeamQuizTeamRepository valorantWhichIsTeamQuizTeamRepository;
    private final TeamValorantQueryService teamValorantQueryService;
    private final ProgamerTeamValorantService progamerTeamValorantService;

    public List<ValorantWhichIsTeamQuizResponse> setQuizLists(Integer totalQuizCount, Game game) {
        WhichIsTeam whichIsTeam = WhichIsTeam.create(totalQuizCount, 0, game);
        WhichIsTeam savedWhichIsTeam = whichIsTeamRepository.save(whichIsTeam);
        List<Long> teamIds = progamerTeamValorantService.findTeamIdsWithFiveOrMoreProgamers();
        List<TeamValorant> teamList = teamValorantQueryService.findRandomTeams(totalQuizCount, teamIds);
        return LongStream.range(0, teamList.size())
                .mapToObj(i -> {
                    TeamValorant teamValorant = teamList.get((int) i);
                    valorantWhichIsTeamQuizTeamRepository.save(ValorantWhichIsTeamQuizTeam.create(savedWhichIsTeam, teamValorant));
                    List<ProgamerValorantSimpleInfoResponse> rosters = progamerTeamValorantService.findProgamersByTeamId(teamValorant.getId());
                    return ValorantWhichIsTeamQuizResponse.of(savedWhichIsTeam.getId(), i + 1, teamValorant.getName(), teamValorant.getCallName(), teamValorant.getSeasonYear(), teamValorant.getLeague(), rosters);
                })
                .collect(Collectors.toList());
    }

    public ValorantWhichIsTeamResponse setQuiz(Long id, List<ValorantWhichIsTeamQuizResponse> quizList) {
        WhichIsTeam whichIsTeam = whichIsTeamRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        return ValorantWhichIsTeamResponse.of(whichIsTeam.getId(), 0, whichIsTeam.getTotalQuizCount(), whichIsTeam.getCorrectQuizCount(), quizList);
    }

    public ValorantWhichIsTeamSubmitAnswerResponse submitAnswer(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount, String input) {
        List<ValorantWhichIsTeamQuizTeam> quizList = valorantWhichIsTeamQuizTeamRepository.findByWhichIsTeamIdWithTeam(id);
        List<TeamValorant> submitTeamLOL = teamValorantQueryService.findByTeamName(input);
        if (submitTeamLOL.stream().anyMatch(team -> Objects.equals(team.getId(), quizList.get(index).getTeamValorant().getId()))) {
            correctQuizCount++;
        }

        return ValorantWhichIsTeamSubmitAnswerResponse.of(id, index, correctQuizCount, totalQuizCount);
    }

    public ValorantWhichIsTeamResultResponse saveResult(Long id, Integer correctQuizCount, Integer totalQuizCount) {
        WhichIsTeam whichIsTeam = whichIsTeamRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        whichIsTeamRepository.updateCorrectQuizCount(id, correctQuizCount);
        return ValorantWhichIsTeamResultResponse.of(whichIsTeam.getId(), correctQuizCount, totalQuizCount);
    }
}
