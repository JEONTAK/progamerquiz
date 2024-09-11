package pq.progamerquiz.team;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Transactional
@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EntityManager em;

    private final ObjectMapper mapper = new ObjectMapper();

    public void initializeTeams() {
        // 1. 데이터베이스에 이미 데이터가 있는지 확인
        //List<Team> existingTeams = em.createQuery("SELECT t FROM Team t", Team.class).getResultList();

       /* if (!existingTeams.isEmpty()) {
            // 데이터가 이미 존재하는 경우, 초기화 작업을 하지 않음
            System.out.println("Teams already exist in the database. Skipping initialization.");
            return;
        }

        // 2. 데이터가 없을 경우, JSON 파일에서 데이터를 불러와 저장
        System.out.println("No teams found in the database. Loading data from JSON.");*/
        try {
            // JSON 파일 읽기
            JsonNode rootNode = mapper.readTree(new File("src/main/resources/database/Team.json"));

            // 2. JSON 배열에서 각 객체를 순회
            for (JsonNode node : rootNode) {
                //TeamDto로 변홤
                TeamDto teamDto = mapper.treeToValue(node, TeamDto.class);
                // TeamDto를 Team 엔티티로 변환
                Team team = TeamMapper.toEntity(teamDto);
                Team existingTeam = em.find(Team.class, team.getId());
                if (existingTeam != null) {
                    // 엔티티가 이미 존재하면 merge로 업데이트
                    em.merge(team);
                } else {
                    // 엔티티가 없으면 persist로 삽입
                    em.persist(team);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
