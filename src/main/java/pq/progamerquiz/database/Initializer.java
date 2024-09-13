package pq.progamerquiz.database;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Initializer implements ApplicationRunner {

    private final UpdateTeamList updateTeamList;

    public Initializer(UpdateTeamList updateTeamList) {
        this.updateTeamList = updateTeamList;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 팀 데이터가 없는 경우에만 JSON 파일에서 데이터를 초기화
        updateTeamList.initializeTeams();
        updateTeamList.initializeProgamers();
    }
}
