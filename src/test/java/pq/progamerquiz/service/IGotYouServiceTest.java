package pq.progamerquiz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pq.progamerquiz.domain.igotyou.service.IGotYouService;
import pq.progamerquiz.domain.progamer.service.ProgamerService;
import pq.progamerquiz.domain.progamer.dto.ProgamerDto;
import pq.progamerquiz.domain.igotyou.dto.IGotYouDto;
import pq.progamerquiz.domain.team.dto.TeamDto;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class IGotYouServiceTest {

    @InjectMocks
    private IGotYouService IGotYouService;

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
        List<IGotYouDto> result = IGotYouService.getProgamers(2);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);
    }

    @Test
    void isExist() {
        // Given
        String userInput = "progamer1";
        when(progamerService.findByPid(userInput)).thenReturn(Optional.of(new ProgamerDto(1L, "progamer1", "Player One")));

        // When
        boolean result = IGotYouService.isExist(userInput);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    void isAnswer() {
        // Given
        String userInput = "progamer1";
        IGotYouDto IGotYouDto = new IGotYouDto(
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
                .thenReturn(Optional.of(new ProgamerDto(1L, "progamer1", "Player One")));

        // When
        boolean result = IGotYouService.isAnswer(userInput, IGotYouDto);

        // Then
        assertThat(result).isTrue();
    }
}