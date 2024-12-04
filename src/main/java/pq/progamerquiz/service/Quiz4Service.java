package pq.progamerquiz.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.dto.ProgamerDto;
import pq.progamerquiz.dto.Quiz4Dto;
import pq.progamerquiz.dto.TeamDto;

import java.util.*;


//Quiz : Who are you?
@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class Quiz4Service {

    final private TeamService teamService;
    final private ProgamerService progamerService;

    public List<Quiz4Dto> getTeams(int totalCount, String league) {
        List<Quiz4Dto> quizList = new ArrayList<>();
        int index = 1;

        // 부족한 경우 추가 로직
        while (quizList.size() < totalCount) {
            List<TeamDto> extraTeam = teamService.findRandomTeams(1, league); // 추가 팀 가져오기 메서드
            if (extraTeam != null) {
                Quiz4Dto extraQuiz = convert(index, extraTeam.get(0));
                if (extraQuiz != null) {
                    quizList.add(extraQuiz);
                    index++;
                }
            }
        }

        return quizList;
    }

    static List<Map<Long, Boolean>> getTwoRandomProgamers(List<ProgamerDto> roster) {
        if(roster.size() < 5) {
            return null;
        }
        List<Map<Long, Boolean>> answer = new ArrayList<>();
        List<ProgamerDto> mutableRoster = new ArrayList<>(roster);
        Collections.shuffle(mutableRoster);
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
                if (progamerService.findByPid(pid).get().getId().equals(entry.getKey()) && !entry.getValue()) {
                    entry.setValue(true);
                    return true;
                }
            }
        }
        return false;
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

    public List<ProgamerDto> getRoster(TeamDto submitTeam) {
        return teamService.find(submitTeam.getId()).getRoster().stream().map(m -> new ProgamerDto(
                m.getId(),
                m.getPid(),
                m.getName()
        )).toList();
    }

}
