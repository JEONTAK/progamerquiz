package pq.progamerquiz.quiz.q3_whichisteam;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.team.Team;
import pq.progamerquiz.team.TeamDto;
import pq.progamerquiz.team.TeamService;

import java.util.*;


//Quiz : Who are you?
@Service
@Log4j2
@Transactional
public class Quiz3Service {

    @Autowired
    private TeamService teamService;

    public List<Quiz3Dto> getTeams(int totalCount, String league) {
        List<TeamDto> teamList = teamService.findRandomTeams(totalCount, league);
        List<Quiz3Dto> quizList = new ArrayList<>();

        for(int i = 1 ; i <= teamList.size(); i++) {
            quizList.add(convert(i, teamList.get(i - 1)));
        }
        return quizList;
    }

    public static Quiz3Dto convert(int idx, TeamDto submitTeam) {
        return new Quiz3Dto(
                (long) idx - 1,
                submitTeam.getId(),
                submitTeam.getName(),
                submitTeam.getCallName(),
                submitTeam.getLeague().toString(),
                submitTeam.getSeasonYear(),
                submitTeam.getSpring_rank(),
                submitTeam.getSummer_rank(),
                submitTeam.getWinter_rank(),
                submitTeam.getWorlds_rank(),
                submitTeam.getMsi_rank(),
                submitTeam.getImage_path()
        );
    }

    public boolean isExist(String teamName) {
        return !teamService.findByNameOrCallName(teamName).isEmpty();
    }

    public Long getTeamId(String teamName) {
        return teamService.findIdByName(teamName);
    }

    public boolean isAnswer(String teamName, Quiz3Dto quiz3Dto) {
        List<Team> teamList =teamService.findByNameOrCallName(teamName);
        for (Team team : teamList) {
            if (team.getName().equals(quiz3Dto.getTeamName())) {
                return true;
            }
        }
        return false;
    }
}
