package pq.progamerquiz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerInsertResponse;
import pq.progamerquiz.domain.progamer.service.ProgamerCommandService;
import pq.progamerquiz.domain.quizzes.whoareyou.service.WheAreYouService;
import pq.progamerquiz.domain.quizzes.whoareyou.dto.response.WhoAreYouResponse;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class WheAreYouServiceTest {

    @Mock
    private ProgamerCommandService progamerCommandService;

    @InjectMocks
    private WheAreYouService wheAreYouService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getImagePath() {
        // Given
        WhoAreYouResponse quiz1Dto = new WhoAreYouResponse(1L, "progamer123", "PlayerName", 1998L, "MID", 5L, 3L, "Korea", "TeamName", 1L, "LCK");

        // When
        String imagePath = wheAreYouService.getImagePath(quiz1Dto);

        // Then
        assertNotNull(imagePath);
        assertEquals("/images/player/1.webp", imagePath);
    }

    @Test
    public void findByPid() {
        // Given
        ProgamerInsertResponse mockProgamerInsertResponse = new ProgamerInsertResponse(1L, "progamer123", "PlayerName", 1998L, "LCK", 5L, 3L, "Korea", Collections.emptyList());
        when(progamerCommandService.findByPid("progamer123")).thenReturn(mockProgamerInsertResponse);

        // When
        ProgamerInsertResponse result = wheAreYouService.findByPid("progamer123");

        // Then
        assertNotNull(result);
        assertEquals("progamer123", result.getProgamerTag());
    }

    /*@Test
    public void convert() {
        // Given
        ProgamerDto mockProgamerDto = new ProgamerDto(1L, "progamer123", "PlayerName", 1998L, "LCK", 5, 3, "Korea", List.of(new TeamDto("TeamName", "/images/team.png", "League")));

        // When
        Quiz1Dto result = Quiz1Service.convert(mockProgamerDto);

        // Then
        assertNotNull(result);
        assertEquals(mockProgamerDto.getId(), result.getId());
        assertEquals(mockProgamerDto.getName(), result.getName());
        assertEquals(mockProgamerDto.getTeams().get(0).getName(), result.getTeamName());
    }*/
}