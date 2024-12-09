package pq.progamerquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pq.progamerquiz.domain.Team;

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
    private List<ProgamerDto> roster = new ArrayList<>();
    private Long image_path;

    public static TeamDto toDto(Team team) {
        return new TeamDto(
                team.getId(), team.getName(), team.getCallName(), team.getSeasonYear(),
                team.getLeague(), team.getSpring_rank(), team.getSummer_rank(),
                team.getMsi_rank(), team.getWorlds_rank(), team.getWinter_rank(),
                team.getRoster().stream().map(ProgamerDto::toDtoNotUsingTeam).toList(), team.getImage_path()
        );
    }

    public static TeamDto toDtoForNotUseRoster(Team team){
        return new TeamDto(
                team.getId(), team.getName(), team.getCallName(), team.getSeasonYear(),
                team.getLeague(), team.getSpring_rank(), team.getSummer_rank(),
                team.getMsi_rank(), team.getWorlds_rank(), team.getWinter_rank(),
                null, team.getImage_path()
        );
    }

    public static List<TeamDto> listToDto(List<Team> teams) {
        return teams.stream()
                .map(TeamDto::toDtoForNotUseRoster)
                .collect(Collectors.toList());
    }
}
