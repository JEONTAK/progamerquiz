package pq.progamerquiz.domain.team.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pq.progamerquiz.domain.team.dto.response.TeamInfoResponse;
import pq.progamerquiz.domain.team.entity.Team;

@Getter
@RequiredArgsConstructor
@Component
public class TeamMapper {

  /*  // Convert List<TeamDto> to List<Team>
    public static List<Team> toEntityList(List<TeamDto> teamDtoList) {
        return teamDtoList.stream()
                .map(TeamMapper::create)
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