package pq.progamerquiz.quiz.q3_whatisteam;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.progamer.Progamer;
import pq.progamerquiz.progamer.ProgamerRepository;
import pq.progamerquiz.quiz.q2_igotyou.Quiz2Dto;
import pq.progamerquiz.team.Team;
import pq.progamerquiz.team.TeamRepository;
import pq.progamerquiz.team.TeamService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


//Quiz : Who are you?
@Service
@Log4j2
@Transactional
public class Quiz3Service {

    @Autowired
    private TeamService teamService;

    public List<Quiz3Dto> getTeams(int idx) {
        List<Team> teamList = teamService.findAll();
        List<Quiz3Dto> quizList = new ArrayList<>();
        Set<Integer> selectedIndexes = new HashSet<>();
        Random random = new Random();

        // 겹치지 않게 프로게이머 선택
        while (selectedIndexes.size() < idx) {
            int randomIndex = random.nextInt(teamList.size());
            if (!selectedIndexes.contains(randomIndex)) {
                selectedIndexes.add(randomIndex);
                Team answer = teamList.get(randomIndex);
                Quiz3Dto quiz3Dto = convert(selectedIndexes.size(), answer); // 인덱스는 1부터 시작
                quizList.add(quiz3Dto);
            }
        }
        return quizList;
    }

    public static Quiz3Dto convert(int idx, Team submitTeam) {
        return new Quiz3Dto(
                (long) idx,
                submitTeam.getId(),
                submitTeam.getName(),
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
        return !teamService.findByName(teamName).isEmpty();
    }

    public Long getTeamId(String teamName) {
        return teamService.findIdByName(teamName);
    }

    public boolean isAnswer(String teamName, Quiz3Dto quiz3Dto) {
        List<Team> teamList =teamService.findByName(teamName);
        for (Team team : teamList) {
            if (team.getName().equals(quiz3Dto.getTeamName())) {
                return true;
            }
        }
        return false;
    }
}
