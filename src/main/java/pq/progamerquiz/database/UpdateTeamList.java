package pq.progamerquiz.database;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.team.Team;
import pq.progamerquiz.team.TeamRepository;

import java.io.File;
import java.io.IOException;

@Transactional
@Service
public class UpdateTeamList {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EntityManager em;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        try {
            // 1. ObjectMapper로 JSON 파일을 JsonNode로 읽기
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new File("src/main/resources/database/Team.json"));

            // 2. JSON 배열에서 각 객체를 순회
            for (JsonNode node : rootNode) {
                Team team = mapper.treeToValue(node, Team.class);
                teamRepository.save(team);
            }
            System.out.println(teamRepository.count());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
