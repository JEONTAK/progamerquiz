package pq.progamerquiz.quiz.q4_pieceofpuzzle;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.progamer.Progamer;
import pq.progamerquiz.progamer.ProgamerDto;
import pq.progamerquiz.progamer.ProgamerService;
import pq.progamerquiz.team.Team;
import pq.progamerquiz.team.TeamDto;
import pq.progamerquiz.team.TeamService;

import java.util.*;


//Quiz : Who are you?
@Service
@Log4j2
@Transactional
public class Quiz4Service {

    @Autowired
    private TeamService teamService;
    @Autowired
    private ProgamerService progamerService;

    public List<Quiz4Dto> getTeams(int totalCount, String league) {
        List<TeamDto> teamList = teamService.findRandomTeams(totalCount, league);
        List<Quiz4Dto> quizList = new ArrayList<>();

        for(int i = 1 ; i <= teamList.size() ; i++){
            quizList.add(convert(i, teamList.get(i - 1)));
        }

        return quizList;
    }

    public Quiz4Dto convert(int idx, TeamDto submitTeam) {
        List<ProgamerDto> roster = getRoster(submitTeam);
        List<Map<Long, Boolean>> answer = getTwoRandomProgamers(roster);
        if (answer == null) {
            return null;
        }

        return new Quiz4Dto(
                (long) idx - 1,
                submitTeam.getId(),
                submitTeam.getName(),
                submitTeam.getSeasonYear(),
                roster,
                answer,
                submitTeam.getImage_path(),
                0,
                0
        );
    }

    private static List<Map<Long, Boolean>> getTwoRandomProgamers(List<ProgamerDto> roster) {
        List<Map<Long, Boolean>> answer = new ArrayList<>();
        List<ProgamerDto> mutableRoster = new ArrayList<>(roster);
        Collections.shuffle(mutableRoster);
        if(roster.size() < 5) {
            return null;
        }
        for (ProgamerDto progamerDto : mutableRoster.subList(0, 2)) {
            Map<Long, Boolean> progamerMap = new HashMap<>();
            progamerMap.put(progamerDto.getId(), false);  // id를 키로 하고 초기 상태는 false
            answer.add(progamerMap);
        }
        return answer;
    }

    public boolean isExist(String pid) {
        return progamerService.findByPid(pid) != null;
    }

    public boolean isAnswer(String pid, Quiz4Dto quiz4Dto) {
        for (Map<Long, Boolean> answer : quiz4Dto.getAnswer()) {
            for (Map.Entry<Long, Boolean> entry : answer.entrySet()) {
                if (progamerService.findByPid(pid).getId().equals(entry.getKey()) && !entry.getValue()) {
                    entry.setValue(true);
                    return true;
                }
            }
        }
        return false;
    }

    public List<ProgamerDto> getRoster(TeamDto submitTeam) {
        return teamService.find(submitTeam.getId()).getRoster().stream().map(m -> new ProgamerDto(
                m.getId(),
                m.getPid(),
                m.getName()
        )).toList();
    }

}
