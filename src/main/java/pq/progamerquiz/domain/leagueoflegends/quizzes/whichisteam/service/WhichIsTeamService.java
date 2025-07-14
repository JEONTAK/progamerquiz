package pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response.ProgamerSimpleInfoResponse;
import pq.progamerquiz.domain.leagueoflegends.progamerteamlol.service.ProgamerTeamLOLService;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response.WhichIsTeamSubmitAnswerResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.entity.WhichIsTeam;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response.WhichIsTeamQuizResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response.WhichIsTeamResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.dto.response.WhichIsTeamResultResponse;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.entity.WhichIsTeamQuizTeam;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.repository.WhichIsTeamQuizTeamRepository;
import pq.progamerquiz.domain.leagueoflegends.quizzes.whichisteam.repository.WhichIsTeamRepository;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;
import pq.progamerquiz.domain.leagueoflegends.teamlol.service.TeamLOLQueryService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WhichIsTeamService {

    private final WhichIsTeamRepository whichIsTeamRepository;
    private final WhichIsTeamQuizTeamRepository whichIsTeamQuizTeamRepository;
    private final TeamLOLQueryService teamLOLQueryService;
    private final ProgamerTeamLOLService progamerTeamLOLService;

    public List<WhichIsTeamQuizResponse> setQuizLists(Integer totalQuizCount) {
        WhichIsTeam whichIsTeam = WhichIsTeam.create(totalQuizCount, 0);
        WhichIsTeam savedWhichIsTeam = whichIsTeamRepository.save(whichIsTeam);
        List<Long> teamIds = progamerTeamLOLService.findTeamIdsWithFiveOrMoreProgamers();
        List<TeamLOL> teamLOLList = teamLOLQueryService.findRandomTeams(totalQuizCount, teamIds);
        return LongStream.range(0, teamLOLList.size())
                .mapToObj(i -> {
                    TeamLOL teamLOL = teamLOLList.get((int) i);
                    whichIsTeamQuizTeamRepository.save(WhichIsTeamQuizTeam.create(savedWhichIsTeam, teamLOL));
                    List<ProgamerSimpleInfoResponse> rosters = progamerTeamLOLService.findProgamersByTeamId(teamLOL.getId());
                    return WhichIsTeamQuizResponse.of(savedWhichIsTeam.getId(), i + 1, teamLOL.getName(), teamLOL.getSeasonYear(), teamLOL.getLeague(), teamLOL.getImageId(), rosters);
                })
                .collect(Collectors.toList());
    }

    public WhichIsTeamResponse setQuiz(Long id, List<WhichIsTeamQuizResponse> quizList) {
        WhichIsTeam whichIsTeam = whichIsTeamRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        return WhichIsTeamResponse.of(whichIsTeam.getId(), 0, whichIsTeam.getTotalQuizCount(), whichIsTeam.getCorrectQuizCount(), quizList);
    }

    public WhichIsTeamSubmitAnswerResponse submitAnswer(Long id, Integer index, Integer correctQuizCount, Integer totalQuizCount, String input) {
        List<WhichIsTeamQuizTeam> quizList = whichIsTeamQuizTeamRepository.findByWhichIsTeamIdWithTeam(id);
        List<TeamLOL> submitTeamLOL = teamLOLQueryService.findByTeamName(input);
        if (submitTeamLOL.stream().anyMatch(team -> Objects.equals(team.getId(), quizList.get(index).getTeamLOL().getId()))) {
            correctQuizCount++;
        }

        return WhichIsTeamSubmitAnswerResponse.of(id, index, correctQuizCount, totalQuizCount);
    }

    public WhichIsTeamResultResponse saveResult(Long id, Integer correctQuizCount, Integer totalQuizCount) {
        WhichIsTeam whichIsTeam = whichIsTeamRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        whichIsTeamRepository.updateCorrectQuizCount(id, correctQuizCount);
        return WhichIsTeamResultResponse.of(whichIsTeam.getId(), correctQuizCount, totalQuizCount);
    }
}
