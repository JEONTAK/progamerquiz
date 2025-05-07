package pq.progamerquiz.domain.igotyou.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pq.progamerquiz.domain.progamer.dto.ProgamerDto;
import pq.progamerquiz.domain.team.dto.TeamDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//Quiz : I Got you!
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IGotYouDto {

    private Long index;
    private Long id;
    private String pid;
    private String name;
    private String position;
    private List<Long> teamYears;
    private List<String> teamNames;
    private List<Long> teamImages;

    public static IGotYouDto convert(int idx, ProgamerDto submitProgamer) {
        List<TeamDto> teams = submitProgamer.getTeams();
        teams.sort(Comparator.comparing(TeamDto::getSeasonYear));
        List<Long> teamYears = new ArrayList<>();
        List<String> teamNames = new ArrayList<>();
        List<Long> teamImages = new ArrayList<>();

        for (TeamDto team : submitProgamer.getTeams()) {
            teamYears.add(team.getSeasonYear());
            teamNames.add(team.getName());
            teamImages.add(team.getImage_path());
        }

        IGotYouDto result = new IGotYouDto(
                (long) idx,
                submitProgamer.getId(),
                submitProgamer.getPid(),
                submitProgamer.getName(),
                submitProgamer.getPosition().toString(),
                teamYears,
                teamNames,
                teamImages
        );
        return result;
    }
}
