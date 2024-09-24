package pq.progamerquiz.database;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.progamer.Progamer;
import pq.progamerquiz.progamer.ProgamerDto;
import pq.progamerquiz.progamer.ProgamerMapper;
import pq.progamerquiz.progamer.ProgamerService;
import pq.progamerquiz.quiz.Quiz;
import pq.progamerquiz.quiz.QuizDto;
import pq.progamerquiz.quiz.QuizMapper;
import pq.progamerquiz.quiz.QuizService;
import pq.progamerquiz.team.Team;
import pq.progamerquiz.team.TeamDto;
import pq.progamerquiz.team.TeamMapper;
import pq.progamerquiz.team.TeamService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Transactional
@Service
@Log4j2
public class UpdateDBList {


    @Autowired
    private EntityManager em;
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private ProgamerService progamerService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private TeamService teamService;

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
            for (JsonNode node : rootNode) {
                TeamDto teamDto = mapper.treeToValue(node, TeamDto.class);
                Team team = TeamMapper.toEntity(teamDto);
                teamService.saveTeam(team);
            }
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
            for (JsonNode node : rootNode) {
                ProgamerDto progamerDto = mapper.treeToValue(node, ProgamerDto.class);
                Progamer progamer = ProgamerMapper.toEntity(progamerDto, em);
                progamerService.saveProgamer(progamer);
            }
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
            for (JsonNode node : rootNode) {
                QuizDto quizDto = mapper.treeToValue(node, QuizDto.class);
                Quiz quiz = QuizMapper.toEntity(quizDto);
                quizService.saveQuiz(quiz);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
