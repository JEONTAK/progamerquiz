package pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.enums.Game;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response.ProgamerLOLSimpleInfoResponse;
import pq.progamerquiz.domain.leagueoflegends.progamerteamlol.service.ProgamerTeamLOLService;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response.LOLWhichIsTeamQuizResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response.LOLWhichIsTeamResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response.LOLWhichIsTeamResultResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response.LOLWhichIsTeamSubmitAnswerResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.entity.LOLWhichIsTeamQuizTeam;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.repository.LOLWhichIsTeamQuizTeamRepository;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;
import pq.progamerquiz.domain.leagueoflegends.teamlol.service.TeamLOLQueryService;
import pq.progamerquiz.domain.quizzes.entity.WhichIsTeam;
import pq.progamerquiz.domain.quizzes.repository.WhichIsTeamRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LOLWhichIsTeamService {

    private final WhichIsTeamRepository whichIsTeamRepository;
    private final LOLWhichIsTeamQuizTeamRepository lolWhichIsTeamQuizTeamRepository;
    private final TeamLOLQueryService teamLOLQueryService;
    private final ProgamerTeamLOLService progamerTeamLOLService;

    public List<LOLWhichIsTeamQuizResponse> setQuizLists(Integer totalQuizCount, Game game) {
        WhichIsTeam whichIsTeam = WhichIsTeam.create(totalQuizCount, 0, game);
        WhichIsTeam savedWhichIsTeam = whichIsTeamRepository.save(whichIsTeam);
        List<Long> teamIds = progamerTeamLOLService.findTeamIdsWithFiveOrMoreProgamers();
        List<TeamLOL> teamLOLList = teamLOLQueryService.findRandomTeams(totalQuizCount, teamIds);
        return LongStream.range(0, teamLOLList.size())
                .mapToObj(i -> {
                    TeamLOL teamLOL = teamLOLList.get((int) i);
                    lolWhichIsTeamQuizTeamRepository.save(LOLWhichIsTeamQuizTeam.create(savedWhichIsTeam, teamLOL));
                    List<ProgamerLOLSimpleInfoResponse> rosters = progamerTeamLOLService.findProgamersByTeamId(teamLOL.getId());
                    return LOLWhichIsTeamQuizResponse.of(savedWhichIsTeam.getId(), i + 1, teamLOL.getName(), teamLOL.getSeasonYear(), teamLOL.getLeague(), teamLOL.getImageId(), rosters);
                })
                .collect(Collectors.toList());
    }

    public LOLWhichIsTeamResponse setQuiz(Long id, List<LOLWhichIsTeamQuizResponse> quizList) {
        WhichIsTeam whichIsTeam = whichIsTeamRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        return LOLWhichIsTeamResponse.of(whichIsTeam.getId(), 0, whichIsTeam.getTotalQuizCount(), whichIsTeam.getCorrectQuizCount(), quizList);
    }

    public LOLWhichIsTeamSubmitAnswerResponse submitAnswer(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount, String input) {
        List<LOLWhichIsTeamQuizTeam> quizList = lolWhichIsTeamQuizTeamRepository.findByWhichIsTeamIdWithTeam(id);
        List<TeamLOL> submitTeamLOL = teamLOLQueryService.findByTeamName(input);
        if (submitTeamLOL.stream().anyMatch(team -> Objects.equals(team.getId(), quizList.get(index).getTeamLOL().getId()))) {
            correctQuizCount++;
        }

        return LOLWhichIsTeamSubmitAnswerResponse.of(id, index, correctQuizCount, totalQuizCount);
    }

    public LOLWhichIsTeamResultResponse saveResult(Long id, Integer correctQuizCount, Integer totalQuizCount) {
        WhichIsTeam whichIsTeam = whichIsTeamRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        whichIsTeamRepository.updateCorrectQuizCount(id, correctQuizCount);
        return LOLWhichIsTeamResultResponse.of(whichIsTeam.getId(), correctQuizCount, totalQuizCount);
    }
}
