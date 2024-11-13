package pq.progamerquiz.quiz.q2_igotyou;


import java.util.*;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.progamer.ProgamerDto;
import pq.progamerquiz.progamer.ProgamerService;
import pq.progamerquiz.team.Team;

//Quiz : I Got you!
@Service
@Log4j2
@Transactional
public class Quiz2Service {

    @Autowired
    private ProgamerService progamerService;

    public List<Quiz2Dto> getProgamers(int totalCount) {
        List<ProgamerDto> progamerList = progamerService.findRandomPlayers(totalCount);
        List<Quiz2Dto> result = new ArrayList<>();

        for(int i = 1 ; i <= progamerList.size(); i++) {
            result.add(convert(i, progamerList.get(i - 1)));
        }
        return result;
    }

    public static Quiz2Dto convert(int idx, ProgamerDto submitProgamer) {
        List<Long> teamYears = new ArrayList<>();
        List<String> teamNames = new ArrayList<>();
        List<Long> teamImages = new ArrayList<>();
        for (Team team : submitProgamer.getTeams()) {
            teamYears.add(team.getSeasonYear());
            teamNames.add(team.getName());
            teamImages.add(team.getImage_path());
        }

        Quiz2Dto result = new Quiz2Dto(
                (long) idx,
                submitProgamer.getId(),
                submitProgamer.getPid(),
                submitProgamer.getName(),
                submitProgamer.getPosition().toString(),
                teamYears,
                teamNames,
                teamImages
        );

        return result;
    }

    public boolean isExist(String userInput) {
        return progamerService.findByPid(userInput) != null;
    }

    public boolean isAnswer(String userInput, Quiz2Dto quiz2Dto) {
        return Objects.equals(progamerService.findByPid(userInput).getId(), quiz2Dto.getId());
    }
}
