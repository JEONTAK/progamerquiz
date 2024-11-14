package pq.progamerquiz.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.Progamer;
import pq.progamerquiz.dto.ProgamerDto;
import pq.progamerquiz.mapper.ProgamerMapper;
import pq.progamerquiz.service.ProgamerService;
import pq.progamerquiz.domain.Quiz;
import pq.progamerquiz.dto.QuizDto;
import pq.progamerquiz.mapper.QuizMapper;
import pq.progamerquiz.service.QuizService;
import pq.progamerquiz.domain.Team;
import pq.progamerquiz.dto.TeamDto;
import pq.progamerquiz.mapper.TeamMapper;
import pq.progamerquiz.service.TeamService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Transactional
@Service
@Log4j2
@RequiredArgsConstructor
public class UpdateDBList {


    private final EntityManager em;
    private final ObjectMapper mapper = new ObjectMapper();
    private final ProgamerService progamerService;
    private final QuizService quizService;
    private final TeamService teamService;

    public void initializeTeams() {
        List<Team> existingTeams = em.createQuery("SELECT t FROM Team t", Team.class).getResultList();
        if (!existingTeams.isEmpty()) {
            log.info("Teams already exist in the database. Skipping initialization.");
            return;
        }
        log.info("No teams found in the database. Loading data from JSON.");
        try {
            File jsonFile = new File("src/main/resources/static/database/Team.json");
            JsonNode rootNode = mapper.readTree(jsonFile);
            rootNode.forEach(node ->{
                TeamDto teamDto = null;
                try {
                    teamDto = mapper.treeToValue(node, TeamDto.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                Team team = TeamMapper.toEntity(teamDto);
                teamService.saveTeam(team);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeProgamers() {
        List<Progamer> existingProgamers = em.createQuery("SELECT p FROM Progamer p", Progamer.class).getResultList();
        if (!existingProgamers.isEmpty()) {
            log.info("Quizzes already exist in the database. Skipping initialization.");
            return;
        }
        log.info("No Quizzes found in the database. Loading data from JSON.");
        try {
            File jsonFile = new File("src/main/resources/static/database/Progamer.json");
            JsonNode rootNode = mapper.readTree(jsonFile);
            rootNode.forEach(node ->{
                ProgamerDto progamerDto = null;
                try {
                    progamerDto = mapper.treeToValue(node, ProgamerDto.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                Progamer progamer = ProgamerMapper.toEntity(progamerDto, em);
                progamerService.saveProgamer(progamer);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeQuizzes() {
        List<Quiz> existingQuizzes= em.createQuery("SELECT q FROM Quiz q", Quiz.class).getResultList();
        if (!existingQuizzes.isEmpty()) {
            log.info("Progamers already exist in the database. Skipping initialization.");
            return;
        }
        log.info("No Progamers found in the database. Loading data from JSON.");
        try {
            File jsonFile = new File("src/main/resources/static/database/Quiz.json");
            JsonNode rootNode = mapper.readTree(jsonFile);
            rootNode.forEach(node -> {
                try {
                    QuizDto quizDto = mapper.treeToValue(node, QuizDto.class);  // 예외가 발생할 수 있는 부분
                    Quiz quiz = QuizMapper.toEntity(quizDto);
                    quizService.saveQuiz(quiz);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
