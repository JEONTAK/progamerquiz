package pq.progamerquiz.domain.igotyou.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//Quiz : I Got you!
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IGotYouResponse {

    private Long index;
    private Long id;
    private String pid;
    private String name;
    private String position;
    private List<Long> teamYears;
    private List<String> teamNames;
    private List<Long> teamImages;

    /*public static IGotYouResponse of(int idx, ProgamerInsertResponse submitProgamer) {
        List<TeamDto> teams = submitProgamer.getTeams();
        teams.sort(Comparator.comparing(TeamDto::getSeasonYear));
        List<Long> teamYears = new ArrayList<>();
        List<String> teamNames = new ArrayList<>();
        List<Long> teamImages = new ArrayList<>();

        for (TeamDto team : submitProgamer.getTeams()) {
            teamYears.add(team.getSeasonYear());
            teamNames.add(team.getName());
            teamImages.add(team.getImageId());
        }

        IGotYouResponse result = new IGotYouResponse(
                (long) idx,
                submitProgamer.getId(),
                submitProgamer.getProgamerTag(),
                submitProgamer.getName(),
                submitProgamer.getPosition().toString(),
                teamYears,
                teamNames,
                teamImages
        );
        return result;
    }*/
}
