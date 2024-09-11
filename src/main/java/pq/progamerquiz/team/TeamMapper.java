package pq.progamerquiz.team;

import java.util.ArrayList;

public class TeamMapper {

    public static Team toEntity(TeamDto teamDto) {
        Team team = new Team();
        team.setId(teamDto.getId());
        team.setName(teamDto.getName());
        team.setSeasonYear(teamDto.getSeasonYear());
        team.setLeague(Team.League.valueOf(teamDto.getLeague())); // enum 변환
        team.setSpring_rank(teamDto.getSpring_rank());
        team.setSummer_rank(teamDto.getSummer_rank());
        team.setMsi_rank(teamDto.getMsi_rank());
        team.setWorlds_rank(teamDto.getWorlds_rank());
        team.setWinter_rank(teamDto.getWinter_rank());
        team.setRoster(new ArrayList<>());
        return team;
    }
}