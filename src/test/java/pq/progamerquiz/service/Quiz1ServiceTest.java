package pq.progamerquiz.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pq.progamerquiz.dto.ProgamerDto;
import pq.progamerquiz.dto.Quiz1Dto;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class Quiz1ServiceTest {

    @Mock
    private ProgamerService progamerService;

    @InjectMocks
    private Quiz1Service quiz1Service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getImagePath() {
        // Given
        Quiz1Dto quiz1Dto = new Quiz1Dto(1L, "progamer123", "PlayerName", 1998L, "MID", 5L, 3L, "Korea", "TeamName", 1L, "LCK");

        // When
        String imagePath = quiz1Service.getImagePath(quiz1Dto);

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
        ProgamerDto result = quiz1Service.findByPid("progamer123");

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