package pq.progamerquiz.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerResponse;
import pq.progamerquiz.domain.team.entity.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {

    private Long id;
    private String name;
    private String callName;
    private Long seasonYear;
    private String league;
    private Long spring_rank;
    private Long summer_rank;
    private Long msi_rank;
    private Long worlds_rank;
    private Long winter_rank;
    private List<ProgamerResponse> roster = new ArrayList<>();
    private Long image_path;

    public static TeamDto toDto(Team team) {
        return new TeamDto(
                team.getId(), team.getName(), team.getCallName(), team.getSeasonYear(),
                team.getLeague(), team.getSpringRank(), team.getSummerRank(),
                team.getMsiRank(), team.getWorldsRank(), team.getWinterRank(),
                team.getRoster().stream().map(ProgamerResponse::toDtoNotUsingTeam).toList(), team.getImageId()
        );
    }

    public static TeamDto toDtoForNotUseRoster(Team team){
        return new TeamDto(
                team.getId(), team.getName(), team.getCallName(), team.getSeasonYear(),
                team.getLeague(), team.getSpringRank(), team.getSummerRank(),
                team.getMsiRank(), team.getWorldsRank(), team.getWinterRank(),
                null, team.getImageId()
        );
    }

    public static List<TeamDto> listToDto(List<Team> teams) {
        return teams.stream()
                .map(TeamDto::toDtoForNotUseRoster)
                .collect(Collectors.toList());
    }
}
