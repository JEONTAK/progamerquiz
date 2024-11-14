package pq.progamerquiz.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.Team;
import pq.progamerquiz.dto.Quiz3Dto;
import pq.progamerquiz.dto.TeamDto;

import java.util.ArrayList;
import java.util.List;


//Quiz : Who are you?
@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class Quiz3Service {

    final private TeamService teamService;

    public List<Quiz3Dto> getTeams(int totalCount, String league) {
        List<TeamDto> teamList = teamService.findRandomTeams(totalCount, league);
        List<Quiz3Dto> quizList = new ArrayList<>();

        for(int i = 1 ; i <= teamList.size(); i++) {
            quizList.add(Quiz3Dto.convert(i, teamList.get(i - 1)));
        }
        return quizList;
    }

    public boolean isExist(String teamName) {
        return !teamService.findByNameOrCallName(teamName).isEmpty();
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
