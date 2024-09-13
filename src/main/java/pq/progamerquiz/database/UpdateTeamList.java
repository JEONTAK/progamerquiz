package pq.progamerquiz.database;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.progamer.Progamer;
import pq.progamerquiz.progamer.ProgamerDto;
import pq.progamerquiz.progamer.ProgamerMapper;
import pq.progamerquiz.team.Team;
import pq.progamerquiz.team.TeamDto;
import pq.progamerquiz.team.TeamMapper;
import pq.progamerquiz.team.TeamRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Transactional
@Service
public class UpdateTeamList {


    @Autowired
    private EntityManager em;
    private final ObjectMapper mapper = new ObjectMapper();

    public void initializeTeams() {
        // 1. 데이터베이스에 이미 데이터가 있는지 확인
        List<Team> existingTeams = em.createQuery("SELECT t FROM Team t", Team.class).getResultList();

        if (!existingTeams.isEmpty()) {
            // 데이터가 이미 존재하는 경우, 초기화 작업을 하지 않음
            System.out.println("Teams already exist in the database. Skipping initialization.");
            return;
        }

        // 2. 데이터가 없을 경우, JSON 파일에서 데이터를 불러와 저장
        System.out.println("No teams found in the database. Loading data from JSON.");
        try {
            // JSON 파일 읽기
            File jsonFile = new File("src/main/resources/database/Team.json");
            JsonNode rootNode = mapper.readTree(jsonFile);

            // 2. JSON 배열에서 각 객체를 순회
            for (JsonNode node : rootNode) {
                //TeamDto로 변홤
                TeamDto teamDto = mapper.treeToValue(node, TeamDto.class);
                // TeamDto를 Team 엔티티로 변환
                Team team = TeamMapper.toEntity(teamDto);
                Team existingTeam = em.find(Team.class, team.getId());
                em.merge(team);
             /*   if (existingTeam != null) {
                    // 엔티티가 이미 존재하면 merge로 업데이트

                } else {
                    // 엔티티가 없으면 persist로 삽입
                    em.persist(team);
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeProgamers() {
        // 1. 데이터베이스에 이미 데이터가 있는지 확인
        List<Progamer> existingProgamers = em.createQuery("SELECT p FROM Progamer p", Progamer.class).getResultList();

        if (!existingProgamers.isEmpty()) {
            // 데이터가 이미 존재하는 경우, 초기화 작업을 하지 않음
            System.out.println("Progamers already exist in the database. Skipping initialization.");
            return;
        }

        // 2. 데이터가 없을 경우, JSON 파일에서 데이터를 불러와 저장
        System.out.println("No Progamers found in the database. Loading data from JSON.");
        try {
            // JSON 파일 읽기
            File jsonFile = new File("src/main/resources/database/Progamer.json");
            JsonNode rootNode = mapper.readTree(jsonFile);

            // JSON 배열 순회 (필요 시)
            for (JsonNode node : rootNode) {
                // ProgamerDto로 변환
                ProgamerDto progamerDto = mapper.treeToValue(node, ProgamerDto.class);

                // ProgamerDto를 Progamer 엔티티로 변환
                Progamer progamer = ProgamerMapper.toEntity(progamerDto, em);

                //DB에 저장
                Team existingProgamer = em.find(Team.class, progamer.getId());
                if (existingProgamer != null) {
                    // 엔티티가 이미 존재하면 merge로 업데이트
                    em.merge(progamer);
                } else {
                    // 엔티티가 없으면 persist로 삽입
                    em.persist(progamer);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
