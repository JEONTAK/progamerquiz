package pq.progamerquiz.database;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pq.progamerquiz.progamer.ProgamerService;
import pq.progamerquiz.team.TeamService;

@Component
public class Initializer implements ApplicationRunner {

    private final TeamService teamService;
    private final ProgamerService progamerService;

    public Initializer(TeamService teamService, ProgamerService progamerService) {
        this.teamService = teamService;
        this.progamerService = progamerService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 팀 데이터가 없는 경우에만 JSON 파일에서 데이터를 초기화
        teamService.initializeTeams();
        progamerService.initializeProgamers();
    }
}
