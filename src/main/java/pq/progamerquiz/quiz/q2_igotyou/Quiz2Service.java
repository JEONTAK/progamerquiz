package pq.progamerquiz.quiz.q2_igotyou;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.progamer.Progamer;
import pq.progamerquiz.progamer.ProgamerRepository;
import pq.progamerquiz.quiz.q1_whoareyou.Quiz1Dto;
import pq.progamerquiz.team.Team;

//Quiz : I Got you!
@Service
@Log4j2
@Transactional
public class Quiz2Service {

    @Autowired
    private ProgamerRepository progamerRepository;

    public List<Quiz2Dto> getProgamers(int totalCount) {
        List<Progamer> progamerList = progamerRepository.findAll();
        List<Quiz2Dto> result = new ArrayList<>();
        Set<Integer> selectedIndexes = new HashSet<>();
        Random random = new Random();

        // 겹치지 않게 프로게이머 선택
        while (selectedIndexes.size() < totalCount) {
            int randomIndex = random.nextInt(progamerList.size());
            if (!selectedIndexes.contains(randomIndex)) {
                selectedIndexes.add(randomIndex);
                Progamer answer = progamerList.get(randomIndex);
                Quiz2Dto quiz2Dto = convert(selectedIndexes.size(), answer); // 인덱스는 1부터 시작
                result.add(quiz2Dto);
            }
        }
        return result;
    }

    public static Quiz2Dto convert(int idx, Progamer submitProgamer) {
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
                teamYears,
                teamNames,
                teamImages
        );
        return result;
    }

}
