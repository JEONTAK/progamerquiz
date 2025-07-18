package pq.progamerquiz.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.common.exception.CustomException;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.dto.response.ProgamerLOLInsertResponse;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.entity.ProgamerLOL;
import pq.progamerquiz.domain.leagueoflegends.progamerlol.repository.ProgamerLOLRepository;
import pq.progamerquiz.domain.leagueoflegends.progamerteamlol.entity.ProgamerTeamLOL;
import pq.progamerquiz.domain.leagueoflegends.progamerteamlol.repository.ProgamerTeamLOLRepository;
import pq.progamerquiz.domain.quizzes.dto.QuizDto;
import pq.progamerquiz.domain.quizzes.entity.Quiz;
import pq.progamerquiz.domain.quizzes.service.QuizService;
import pq.progamerquiz.domain.leagueoflegends.teamlol.dto.response.TeamLOLInsertResponse;
import pq.progamerquiz.domain.leagueoflegends.teamlol.entity.TeamLOL;
import pq.progamerquiz.domain.leagueoflegends.teamlol.repository.TeamLOLRepository;
import pq.progamerquiz.domain.valorant.progamerteamvalorant.entity.ProgamerTeamValorant;
import pq.progamerquiz.domain.valorant.progamerteamvalorant.repository.ProgamerTeamValorantRepository;
import pq.progamerquiz.domain.valorant.progamervalorant.dto.response.ProgamerValorantInsertResponse;
import pq.progamerquiz.domain.valorant.progamervalorant.entity.ProgamerValorant;
import pq.progamerquiz.domain.valorant.progamervalorant.repository.ProgamerValorantRepository;
import pq.progamerquiz.domain.valorant.teamvalorant.dto.response.TeamValorantInsertResponse;
import pq.progamerquiz.domain.valorant.teamvalorant.entity.TeamValorant;
import pq.progamerquiz.domain.valorant.teamvalorant.repository.TeamValorantRepository;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateDBList {

    private final ObjectMapper mapper = new ObjectMapper();
    private final QuizService quizService;

    private final ProgamerLOLRepository progamerLOLRepository;
    private final TeamLOLRepository teamLOLRepository;
    private final ProgamerTeamLOLRepository progamerTeamLOLRepository;

    private final ProgamerValorantRepository progamerValorantRepository;
    private final TeamValorantRepository teamValorantRepository;
    private final ProgamerTeamValorantRepository progamerTeamValorantRepository;

    public void initializeTeamsLOL() {
        try {
            // JSON 파일에서 ProGamer 객체 리스트로 읽기
            List<TeamLOLInsertResponse> teams = mapper.readValue(
                    new File("src/main/resources/static/database/Team-LOL.json"),
                    new TypeReference<>() {}
            );

            // 파싱된 데이터 출력
            teams.stream().map(response -> TeamLOL.create(
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
            )).forEach(teamLOLRepository::save);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeProgamersLOL() {
        try {
            // JSON 파일에서 ProGamer 객체 리스트로 읽기
            List<ProgamerLOLInsertResponse> progamers = mapper.readValue(
                    new File("src/main/resources/static/database/Progamer-LOL.json"),
                    new TypeReference<>() {}
            );

            // 파싱된 데이터 출력
            progamers.forEach(response -> {
                ProgamerLOL progamerLOL = ProgamerLOL.create(
                        response.getId(),
                        response.getProgamerTag(),
                        response.getName(),
                        response.getBirth(),
                        response.getPosition(),
                        response.getLeagueWin(),
                        response.getIntlWin(),
                        response.getNationality()
                );
                ProgamerLOL savedProgamerLOL = progamerLOLRepository.save(progamerLOL);
                log.info("프로게이머 저장 : " + savedProgamerLOL.getId() + " | " + progamerLOL.getProgamerTag());
                List<Long> teamIds = Arrays.stream(response.getTeamIds().split(","))
                        .map(String::trim)  // 각 항목의 앞뒤 공백 제거
                        .filter(s -> !s.isEmpty())  // 빈 문자열 필터링
                        .map(Long::parseLong)  // String을 Long으로 변환
                        .toList();

                teamIds.forEach(teamId -> {
                    TeamLOL teamLOL = teamLOLRepository.findById(teamId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, teamId + " | 해당 팀을 찾을 수 없습니다."));
                    ProgamerTeamLOL progamerTeamLOL = ProgamerTeamLOL.create(savedProgamerLOL, teamLOL);
                    progamerTeamLOLRepository.save(progamerTeamLOL);
                });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeTeamsValorant() {
        try {
            // JSON 파일에서 ProGamer 객체 리스트로 읽기
            List<TeamValorantInsertResponse> teams = mapper.readValue(
                    new File("src/main/resources/static/database/Team-Valorant.json"),
                    new TypeReference<>() {}
            );

            // 파싱된 데이터 출력
            teams.stream().map(response -> TeamValorant.create(
                    response.getId(),
                    response.getName(),
                    response.getCallName(),
                    response.getSeasonYear(),
                    response.getLeague(),
                    response.getSeasonName(),
                    response.getRank()
            )).forEach(teamValorantRepository::save);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeProgamersValorant() {
        try {
            // JSON 파일에서 ProGamer 객체 리스트로 읽기
            List<ProgamerValorantInsertResponse> progamers = mapper.readValue(
                    new File("src/main/resources/static/database/Progamer-Valorant.json"),
                    new TypeReference<>() {}
            );

            // 파싱된 데이터 출력
            progamers.forEach(response -> {
                ProgamerValorant progamerValorant = ProgamerValorant
                        .create(
                        response.getId(),
                        response.getProgamerTag(),
                        response.getName(),
                        response.getBirth(),
                        response.getLeagueWin(),
                        response.getMastersWin(),
                        response.getChampionsWin(),
                        response.getNationality()
                );
                ProgamerValorant savedProgamerValorant = progamerValorantRepository.save(progamerValorant);
                log.info("프로게이머 저장 : " + savedProgamerValorant.getId() + " | " + progamerValorant.getProgamerTag());
                List<Long> teamIds = Arrays.stream(response.getTeams().split(","))
                        .map(String::trim)  // 각 항목의 앞뒤 공백 제거
                        .filter(s -> !s.isEmpty())  // 빈 문자열 필터링
                        .map(Long::parseLong)  // String을 Long으로 변환
                        .toList();

                teamIds.forEach(teamId -> {
                    TeamValorant teamValorant = teamValorantRepository.findById(teamId).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, teamId + " | 해당 팀을 찾을 수 없습니다."));
                    ProgamerTeamValorant progamerTeamValorant = ProgamerTeamValorant.create(savedProgamerValorant, teamValorant);
                    progamerTeamValorantRepository.save(progamerTeamValorant);
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
