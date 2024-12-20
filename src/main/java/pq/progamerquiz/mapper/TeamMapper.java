package pq.progamerquiz.mapper;

import pq.progamerquiz.domain.Team;
import pq.progamerquiz.dto.TeamDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamMapper {

    public static Team toEntity(TeamDto teamDto) {
        Team team = new Team();
        team.setId(teamDto.getId());
        team.setName(teamDto.getName());
        team.setCallName(teamDto.getCallName());
        team.setSeasonYear(teamDto.getSeasonYear());
        team.setLeague(teamDto.getLeague()); // enum 변환
        team.setSpring_rank(teamDto.getSpring_rank());
        team.setSummer_rank(teamDto.getSummer_rank());
        team.setMsi_rank(teamDto.getMsi_rank());
        team.setWorlds_rank(teamDto.getWorlds_rank());
        team.setWinter_rank(teamDto.getWinter_rank());
        team.setRoster(new ArrayList<>());
        team.setImage_path(teamDto.getImage_path());
        return team;
    }

    // Convert List<TeamDto> to List<Team>
    public static List<Team> toEntityList(List<TeamDto> teamDtoList) {
        return teamDtoList.stream()
                .map(TeamMapper::toEntity)
                .collect(Collectors.toList());
    }
}