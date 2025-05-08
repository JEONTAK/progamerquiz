package pq.progamerquiz.domain.team.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pq.progamerquiz.domain.team.dto.response.TeamInfoResponse;
import pq.progamerquiz.domain.team.entity.Team;

@Getter
@RequiredArgsConstructor
public class TeamMapper {

  /*  public static Team toEntity(TeamDto teamDto) {
        Team team = new Team();
        team.setId(teamDto.getId());
        team.setName(teamDto.getName());
        team.setCallName(teamDto.getCallName());
        team.setSeasonYear(teamDto.getSeasonYear());
        team.setLeague(teamDto.getLeague()); // enum 변환
        team.setSpringRank(teamDto.getSpring_rank());
        team.setSummerRank(teamDto.getSummer_rank());
        team.setMsiRank(teamDto.getMsi_rank());
        team.setWorldsRank(teamDto.getWorlds_rank());
        team.setWinterRank(teamDto.getWinter_rank());
        team.setRoster(new ArrayList<>());
        team.setImageId(teamDto.getImage_path());
        return team;
    }

    // Convert List<TeamDto> to List<Team>
    public static List<Team> toEntityList(List<TeamDto> teamDtoList) {
        return teamDtoList.stream()
                .map(TeamMapper::toEntity)
                .collect(Collectors.toList());
    }*/

    public TeamInfoResponse toDto(Team team) {
        return TeamInfoResponse.of(
                team.getId(),
                team.getName(),
                team.getCallName(),
                team.getLeague(),
                team.getSeasonYear(),
                team.getSpringRank(),
                team.getSummerRank(),
                team.getMsiRank(),
                team.getWorldsRank(),
                team.getWinterRank(),
                team.getImageId()
        );
    }
}