package pq.progamerquiz.team;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TeamInitializer implements ApplicationRunner {

    private final TeamService teamService;

    public TeamInitializer(TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 팀 데이터가 없는 경우에만 JSON 파일에서 데이터를 초기화
        teamService.initializeTeams();
    }
}
