package pq.progamerquiz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pq.progamerquiz.dto.ProgamerDto;
import pq.progamerquiz.dto.Quiz2Dto;
import pq.progamerquiz.dto.TeamDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class Quiz2ServiceTest {

    @InjectMocks
    private Quiz2Service quiz2Service;

    @Mock
    private ProgamerService progamerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProgamers() {
        // Given
        List<ProgamerDto> mockProgamerList = List.of(
                new ProgamerDto(
                        1L, // id
                        "progamer1", // pid
                        "Player One", // name
                        19900101L, // birth
                        "Top", // position
                        3L, // league_win
                        2L, // intl_win
                        "Korea", // nationality
                        List.of( // teams
                                new TeamDto(),
                                new TeamDto()
                        )
                ),
                new ProgamerDto(
                        2L, // id
                        "progamer2", // pid
                        "Player Two", // name
                        19920101L, // birth
                        "Jungle", // position
                        1L, // league_win
                        0L, // intl_win
                        "China", // nationality
                        List.of( // teams
                                new TeamDto()
                        )
                )
        );
        when(progamerService.findRandomPlayers(2)).thenReturn(mockProgamerList);

        // When
        List<Quiz2Dto> result = quiz2Service.getProgamers(2);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);
    }

    @Test
    void isExist() {
        // Given
        String userInput = "progamer1";
        when(progamerService.findByPid(userInput)).thenReturn(new ProgamerDto(1L, "progamer1", "Player One"));

        // When
        boolean result = quiz2Service.isExist(userInput);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void isAnswer() {
        // Given
        String userInput = "progamer1";
        Quiz2Dto quiz2Dto = new Quiz2Dto(
                1L,  // index
                1L,  // id
                "progamer1",  // pid
                "Player One",  // name
                "Top",  // position
                List.of(2020L, 2021L),  // teamYears
                List.of("Team A", "Team B"),  // teamNames
                List.of(101L, 102L)  // teamImages
        );

        when(progamerService.findByPid(userInput))
                .thenReturn(new ProgamerDto(1L, "progamer1", "Player One"));

        // When
        boolean result = quiz2Service.isAnswer(userInput, quiz2Dto);

        // Then
        assertThat(result).isTrue();
    }
}