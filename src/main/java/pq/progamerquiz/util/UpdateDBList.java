package pq.progamerquiz.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerInsertResponse;
import pq.progamerquiz.domain.progamer.entity.Progamer;
import pq.progamerquiz.domain.progamer.repository.ProgamerRepository;
import pq.progamerquiz.domain.progamerteam.entity.ProgamerTeam;
import pq.progamerquiz.domain.progamerteam.repository.ProgamerTeamRepository;
import pq.progamerquiz.domain.quiz.dto.QuizDto;
import pq.progamerquiz.domain.quiz.entity.Quiz;
import pq.progamerquiz.domain.quiz.service.QuizService;
import pq.progamerquiz.domain.team.dto.response.TeamInsertResponse;
import pq.progamerquiz.domain.team.entity.Team;
import pq.progamerquiz.domain.team.repository.TeamRepository;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateDBList {


    private final EntityManager em;
    private final ObjectMapper mapper = new ObjectMapper();
    private final ProgamerRepository progamerRepository;
    private final QuizService quizService;
    private final TeamRepository teamRepository;
    private final ProgamerTeamRepository progamerTeamRepository;

    public void initializeTeams() {
        try {
            // JSON 파일에서 ProGamer 객체 리스트로 읽기
            List<TeamInsertResponse> teams = mapper.readValue(
                    new File("src/main/resources/static/database/Team.json"),
                    new TypeReference<List<TeamInsertResponse>>() {}
            );

            // 파싱된 데이터 출력
            teams.stream().map(response -> Team.create(
                    response.getId(),
                    response.getName(),
                    response.getCallName(),
                    response.getSeasonYear(),
                    response.getLeague(),
                    response.getSpringRank(),
                    response.getSummerRank(),
                    response.getMsiRank(),
                    response.getWorldsRank(),
                    response.getWinterRank(),
                    response.getImageId()
            )).forEach(teamRepository::save);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeProgamers() {
        try {
            // JSON 파일에서 ProGamer 객체 리스트로 읽기
            List<ProgamerInsertResponse> progamers = mapper.readValue(
                    new File("src/main/resources/static/database/Progamer.json"),
                    new TypeReference<List<ProgamerInsertResponse>>() {}
            );

            // 파싱된 데이터 출력
            progamers.forEach(response -> {
                Progamer progamer = Progamer.create(
                        response.getId(),
                        response.getProgamerTag(),
                        response.getName(),
                        response.getBirth(),
                        response.getPosition(),
                        response.getLeagueWin(),
                        response.getIntlWin(),
                        response.getNationality()
                );
                Progamer savedProgamer = progamerRepository.save(progamer);
                log.info("프로게이머 저장 : " + savedProgamer.getId() + " | " + progamer.getProgamerTag());
                List<Long> teamIds = Arrays.stream(response.getTeamIds().split(","))
                        .map(String::trim)  // 각 항목의 앞뒤 공백 제거
                        .filter(s -> !s.isEmpty())  // 빈 문자열 필터링
                        .map(Long::parseLong)  // String을 Long으로 변환
                        .toList();

                teamIds.forEach(teamId -> {
                    Team team = teamRepository.findById(teamId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, teamId + " | 해당 팀을 찾을 수 없습니다."));
                    ProgamerTeam progamerTeam = ProgamerTeam.create(savedProgamer, team);
                    progamerTeamRepository.save(progamerTeam);
                });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeQuizzes() {
        try {
            File jsonFile = new File("src/main/resources/static/database/Quiz.json");
            JsonNode rootNode = mapper.readTree(jsonFile);
            rootNode.forEach(node -> {
                try {
                    QuizDto quizDto = mapper.treeToValue(node, QuizDto.class);  // 예외가 발생할 수 있는 부분
                    Quiz quiz = Quiz.create(quizDto.getId(), quizDto.getUrl(), quizDto.getImageUrl(), quizDto.getTitle(), quizDto.getAltText(), quizDto.getDescription());
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
