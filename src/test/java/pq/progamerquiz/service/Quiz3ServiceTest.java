package pq.progamerquiz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pq.progamerquiz.dto.Quiz3Dto;
import pq.progamerquiz.dto.TeamDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class Quiz3ServiceTest {

    @InjectMocks
    private Quiz3Service quiz3Service;

    @Mock
    private TeamService teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTeams() {
        // Given
        List<TeamDto> mockTeams = List.of(
                new TeamDto(1L, "Team A", "Call A", 2023L, "LCK", 1L,
                        2L, 3L, 4L, 5L, List.of(), 101L),
                new TeamDto(2L, "Team B", "Call B", 2023L, "LPL", 6L,
                        7L, 8L, 9L, 10L, List.of(), 102L)
        );
        when(teamService.findRandomTeams(2, "LCK")).thenReturn(mockTeams);

        // When
        List<Quiz3Dto> result = quiz3Service.getTeams(2, "LCK");

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTeamName()).isEqualTo("Team A");
        assertThat(result.get(1).getTeamName()).isEqualTo("Team B");
    }

    @Test
    void isExist() {
        // Given
        String teamName = "Team A";
        when(teamService.findByNameOrCallName(teamName)).thenReturn(List.of(new TeamDto(1L, "Team A", "Call A",
                        2023L, "LCK", 1L, 2L,
                        3L, 4L, 5L, List.of(), 101L)
        ));

        // When
        boolean result = quiz3Service.isExist(teamName);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void isAnswer() {
        // Given
        String teamName = "Team A";
        Quiz3Dto quiz3Dto = new Quiz3Dto(
                0L, 1L, "Team A", "Call A", "LCK", 2023L, 1L,
                2L, 3L, 4L, 5L, 101L);
        when(teamService.findByNameOrCallName(teamName))
                .thenReturn(List.of(new TeamDto(1L, "Team A", "Call A", 2023L, "LCK",
                        1L, 2L, 3L, 4L, 5L, List.of(), 101L)));
        // When
        boolean result = quiz3Service.isAnswer(teamName, quiz3Dto);

        // Then
        assertThat(result).isTrue();
    }
}