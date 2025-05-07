package pq.progamerquiz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pq.progamerquiz.domain.progamer.service.ProgamerService;
import pq.progamerquiz.domain.whoareyou.service.WheAreYouService;
import pq.progamerquiz.domain.progamer.dto.ProgamerDto;
import pq.progamerquiz.domain.whoareyou.dto.WheAreYouDto;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class WheAreYouServiceTest {

    @Mock
    private ProgamerService progamerService;

    @InjectMocks
    private WheAreYouService wheAreYouService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getImagePath() {
        // Given
        WheAreYouDto quiz1Dto = new WheAreYouDto(1L, "progamer123", "PlayerName", 1998L, "MID", 5L, 3L, "Korea", "TeamName", 1L, "LCK");

        // When
        String imagePath = wheAreYouService.getImagePath(quiz1Dto);

        // Then
        assertNotNull(imagePath);
        assertEquals("/images/player/1.webp", imagePath);
    }

    @Test
    public void findByPid() {
        // Given
        ProgamerDto mockProgamerDto = new ProgamerDto(1L, "progamer123", "PlayerName", 1998L, "LCK", 5L, 3L, "Korea", Collections.emptyList());
        when(progamerService.findByPid("progamer123")).thenReturn(mockProgamerDto);

        // When
        ProgamerDto result = wheAreYouService.findByPid("progamer123");

        // Then
        assertNotNull(result);
        assertEquals("progamer123", result.getPid());
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