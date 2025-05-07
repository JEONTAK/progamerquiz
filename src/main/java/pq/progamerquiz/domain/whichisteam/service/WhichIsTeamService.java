package pq.progamerquiz.domain.whichisteam.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.whichisteam.dto.WhichIsTeamDto;
import pq.progamerquiz.domain.team.dto.TeamDto;
import pq.progamerquiz.domain.team.service.TeamService;

import java.util.ArrayList;
import java.util.List;


//Quiz : Which Is Team?
@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class WhichIsTeamService {

    final private TeamService teamService;

    public List<WhichIsTeamDto> getTeams(int totalCount, String league) {
        List<TeamDto> teamList = teamService.findRandomTeams(totalCount, league);
        List<WhichIsTeamDto> quizList = new ArrayList<>();

        for(int i = 1 ; i <= teamList.size(); i++) {
            quizList.add(WhichIsTeamDto.convert(i, teamList.get(i - 1)));
        }
        return quizList;
    }


    public List<TeamDto> findByName(String teamName){
        return teamService.findByNameOrCallName(teamName);
    }

    public boolean isAnswer(List<TeamDto> teamList, WhichIsTeamDto whichIsTeamDto) {
        for (TeamDto teamDto : teamList) {
            if (teamDto.getName().equals(whichIsTeamDto.getTeamName())) {
                return true;
            }
        }
        return false;
    }
}
