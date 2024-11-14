package pq.progamerquiz.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//Quiz : I Got you!
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quiz2Dto {

    private Long index;
    private Long id;
    private String pid;
    private String name;
    private String position;
    private List<Long> teamYears;
    private List<String> teamNames;
    private List<Long> teamImages;

    public static Quiz2Dto convert(int idx, ProgamerDto submitProgamer) {
        List<Long> teamYears = new ArrayList<>();
        List<String> teamNames = new ArrayList<>();
        List<Long> teamImages = new ArrayList<>();
        for (TeamDto team : submitProgamer.getTeams()) {
            teamYears.add(team.getSeasonYear());
            teamNames.add(team.getName());
            teamImages.add(team.getImage_path());
        }

        Quiz2Dto result = new Quiz2Dto(
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
