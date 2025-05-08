package pq.progamerquiz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pq.progamerquiz.domain.pieceofpuzzle.service.PieceOfPuzzleService;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerResponse;
import pq.progamerquiz.domain.progamer.service.ProgamerService;
import pq.progamerquiz.domain.team.service.TeamService;
import pq.progamerquiz.domain.pieceofpuzzle.dto.response.PieceOfPuzzleResponse;
import pq.progamerquiz.domain.team.dto.TeamDto;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class PieceOfPuzzleServiceTest {

    @InjectMocks
    private PieceOfPuzzleService pieceOfPuzzleService;

    @Mock
    private TeamService teamService;
    @Mock
    private ProgamerService progamerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTeams() {
        // Given
        List<PieceOfPuzzleResponse> mockQuiz4 = pieceOfPuzzleService.getTeams(2, "LCK");

        when(teamService.findRandomTeams(2, "LCK")).thenReturn(mockTeams);

        // When
        List<PieceOfPuzzleResponse> result = pieceOfPuzzleService.getTeams(2, "LCK");

        // Then
        assertThat(result).hasSize(2);

        // 추가 디버깅
        System.out.println("Result: " + result);

        assertThat(result.get(0).getTeamName()).isEqualTo("Team A");
        assertThat(result.get(1).getTeamName()).isEqualTo("Team B");
    }

    @Test
    void getTwoRandomProgamers() {
        // Given
        List<ProgamerResponse> roster = List.of(
                new ProgamerResponse(1L, "progamer1", "Player One"),
                new ProgamerResponse(2L, "progamer2", "Player Two"),
                new ProgamerResponse(3L, "progamer3", "Player Three")
        );

        // When
        List<Map<Long, Boolean>> result = pieceOfPuzzleService.getTwoRandomProgamers(roster);

        // Then
        // 결과 크기가 2명인지 검증
        assertThat(result).hasSize(2);

        // 각 Map이 올바른 키와 값을 가지는지 확인
        for (Map<Long, Boolean> map : result) {
            assertThat(map).hasSize(1); // 각 Map은 한 개의 키-값 쌍만 포함
            Long key = map.keySet().iterator().next();
            assertThat(roster.stream().anyMatch(progamer -> progamer.getId().equals(key))).isTrue();
            assertThat(map.get(key)).isInstanceOf(Boolean.class);
        }
    }

    @Test
    void isExist() {
        // Given
        String progamerId = "progamer1";
        when(pieceOfPuzzleService.isExist(progamerId)).thenReturn(true);

        // When
        boolean result = pieceOfPuzzleService.isExist(progamerId);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void isAnswer() {
        // Given
        String userInput = "progamer1";
        List<Map<Long, Boolean>> answerList = List.of(Map.of(1L, true));
        PieceOfPuzzleResponse pieceOfPuzzleResponse = new PieceOfPuzzleResponse(
                1L, null, "Team A", null, null, answerList, null, 0, 0
        );

        when(progamerService.findByPid(userInput))
                .thenReturn(new ProgamerResponse(1L, "progamer1", "Player One"));

        // When
        boolean result = pieceOfPuzzleService.isAnswer(userInput, pieceOfPuzzleResponse);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void convert() {
        // Given
        TeamDto teamDto = new TeamDto(1L, "Team A", "Call A", 2023L, "LCK", 1L, 2L, 3L, 4L, 5L, List.of(), 101L);

        // When
        PieceOfPuzzleResponse result = pieceOfPuzzleService.convert(1, teamDto);

        // Then
        assertThat(result.getIndex()).isEqualTo(1L);
        assertThat(result.getTeamName()).isEqualTo("Team A");
    }

    @Test
    void getRoster() {
        // Given
        TeamDto teamDto = new TeamDto(1L, "Team A", "Call A", 2023L, "LCK", 1L, 2L, 3L, 4L, 5L,
                List.of(
                        new ProgamerResponse(1L, "progamer1", "Player One"),
                        new ProgamerResponse(2L, "progamer2", "Player Two")
                ), 101L
        );
        when(teamService.find(1L)).thenReturn(teamDto);

        // When
        List<ProgamerResponse> result = pieceOfPuzzleService.getRoster(teamDto);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getPid()).isEqualTo("progamer1");
        assertThat(result.get(1).getPid()).isEqualTo("progamer2");
    }
}