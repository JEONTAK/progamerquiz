package pq.progamerquiz.domain.quizzes.whichisteam.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerSimpleInfoResponse;
import pq.progamerquiz.domain.progamerteam.service.ProgamerTeamService;
import pq.progamerquiz.domain.quizzes.whichisteam.dto.response.WhichIsTeamQuizResponse;
import pq.progamerquiz.domain.quizzes.whichisteam.dto.response.WhichIsTeamResponse;
import pq.progamerquiz.domain.quizzes.whichisteam.entity.WhichIsTeam;
import pq.progamerquiz.domain.quizzes.whichisteam.entity.WhichIsTeamQuizTeam;
import pq.progamerquiz.domain.quizzes.whichisteam.repository.WhichIsTeamQuizTeamRepository;
import pq.progamerquiz.domain.quizzes.whichisteam.repository.WhichIsTeamRepository;
import pq.progamerquiz.domain.team.entity.Team;
import pq.progamerquiz.domain.team.service.TeamQueryService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


@Slf4j
@Service
@RequiredArgsConstructor
public class WhichIsTeamService {

    private final WhichIsTeamRepository whichIsTeamRepository;
    private final WhichIsTeamQuizTeamRepository whichIsTeamQuizTeamRepository;
    private final TeamQueryService teamQueryService;
    private final ProgamerTeamService progamerTeamService;

    public List<WhichIsTeamQuizResponse> setQuizLists(Integer totalQuizCount) {
        WhichIsTeam whichIsTeam = WhichIsTeam.create(totalQuizCount, 0);
        WhichIsTeam savedWhichIsTeam = whichIsTeamRepository.save(whichIsTeam);
        List<Team> teamList = teamQueryService.findRandomTeams(totalQuizCount);
        return LongStream.range(0, teamList.size())
                .mapToObj(i -> {
                    Team team = teamList.get((int) i);
                    whichIsTeamQuizTeamRepository.save(WhichIsTeamQuizTeam.create(savedWhichIsTeam, team));
                    List<ProgamerSimpleInfoResponse> rosters = progamerTeamService.findProgamersByTeamId(team.getId());
                    return WhichIsTeamQuizResponse.of(savedWhichIsTeam.getId(), i + 1, team.getName(), team.getSeasonYear(), team.getLeague(), team.getImageId(), rosters);
                })
                .collect(Collectors.toList());
    }

    public WhichIsTeamResponse setQuiz(Long id, List<WhichIsTeamQuizResponse> quizList) {
        WhichIsTeam whichIsTeam = whichIsTeamRepository.findById(id).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "해당 퀴즈를 찾을 수 없습니다."));
        return WhichIsTeamResponse.of(whichIsTeam.getId(), 0, whichIsTeam.getTotalQuizCount(), whichIsTeam.getCorrectQuizCount(), quizList);

    }

   /* public List<WhichIsTeamResponse> getTeams(int totalCount, String league) {
        List<TeamDto> teamList = teamService.findRandomTeams(totalCount, league);
        List<WhichIsTeamResponse> quizList = new ArrayList<>();

        for(int i = 1 ; i <= teamList.size(); i++) {
            quizList.add(WhichIsTeamResponse.of(i, teamList.get(i - 1)));
        }
        return quizList;
    }


    public List<TeamDto> findByName(String teamName){
        return teamService.findByNameOrCallName(teamName);
    }

    public boolean isAnswer(List<TeamDto> teamList, WhichIsTeamResponse whichIsTeamResponse) {
        for (TeamDto teamDto : teamList) {
            if (teamDto.getName().equals(whichIsTeamResponse.getTeamName())) {
                return true;
            }
        }
        return false;
    }*/
}
