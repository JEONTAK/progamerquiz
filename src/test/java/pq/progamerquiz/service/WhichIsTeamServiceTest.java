package pq.progamerquiz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pq.progamerquiz.domain.team.dto.response.TeamInsertResponse;
import pq.progamerquiz.domain.team.service.TeamService;
import pq.progamerquiz.domain.quizzes.whichisteam.service.WhichIsTeamService;
import pq.progamerquiz.domain.quizzes.whichisteam.dto.response.WhichIsTeamResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class WhichIsTeamServiceTest {

    @InjectMocks
    private WhichIsTeamService whichIsTeamService;

    @Mock
    private TeamService teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTeams() {
        // Given
        List<TeamInsertResponse> mockTeams = List.of(
                new TeamInsertResponse(1L, "Team A", "Call A", 2023L, "LCK", 1L,
                        2L, 3L, 4L, 5L, List.of(), 101L),
                new TeamInsertResponse(2L, "Team B", "Call B", 2023L, "LPL", 6L,
                        7L, 8L, 9L, 10L, List.of(), 102L)
        );
        when(teamService.findRandomTeams(2, "LCK")).thenReturn(mockTeams);

        // When
        List<WhichIsTeamResponse> result = whichIsTeamService.getTeams(2, "LCK");

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTeamName()).isEqualTo("Team A");
        assertThat(result.get(1).getTeamName()).isEqualTo("Team B");
    }

    @Test
    void isExist() {
        // Given
        String teamName = "Team A";
        when(teamService.findByNameOrCallName(teamName)).thenReturn(List.of(new TeamInsertResponse(1L, "Team A", "Call A",
                        2023L, "LCK", 1L, 2L,
                        3L, 4L, 5L, List.of(), 101L)
        ));

        // When
        boolean result = whichIsTeamService.isExist(teamName);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void isAnswer() {
        // Given
        String teamName = "Team A";
        WhichIsTeamResponse whichIsTeamResponse = new WhichIsTeamResponse(
                0L, 1L, "Team A", "Call A", "LCK", 2023L, 1L,
                2L, 3L, 4L, 5L, 101L);
        when(teamService.findByNameOrCallName(teamName))
                .thenReturn(List.of(new TeamInsertResponse(1L, "Team A", "Call A", 2023L, "LCK",
                        1L, 2L, 3L, 4L, 5L, List.of(), 101L)));
        // When
        boolean result = whichIsTeamService.isAnswer(teamName, whichIsTeamResponse);

        // Then
        assertThat(result).isTrue();
    }
}