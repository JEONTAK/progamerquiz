package pq.progamerquiz.domain.progamer.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerNotIncludeTeamResponse;
import pq.progamerquiz.domain.progamer.entity.Progamer;
import pq.progamerquiz.domain.team.entity.Team;
import pq.progamerquiz.domain.team.mapper.TeamMapper;
import pq.progamerquiz.domain.team.service.TeamQueryService;

@Getter
@RequiredArgsConstructor
public class ProgamerMapper {

    private final TeamQueryService teamQueryService;
    private final TeamMapper teamMapper;

/*
    public static Progamer toEntity(ProgamerResponse progamerResponse) {
        Progamer progamer = new Progamer();
        progamer.setId(progamerResponse.getId());
        progamer.setProgamerTag(progamerResponse.getPid());
        progamer.setName(progamerResponse.getName());
        progamer.setBirth(progamerResponse.getBirth());
        progamer.setPosition(Progamer.Position.valueOf(progamerResponse.getPosition()));
        progamer.setLeagueWin(progamerResponse.getLeague_win());
        progamer.setIntlWin(progamerResponse.getIntl_win());
        progamer.setNationality(progamerResponse.getNationality());
        progamer.setTeams(TeamMapper.toEntityList(progamerResponse.getTeams()));

        return progamer;
    }
*/

    public ProgamerNotIncludeTeamResponse toDto(Progamer progamer){
        Team recentTeam = teamQueryService.findRecentTeamByProgamer(progamer.getId());
        return ProgamerNotIncludeTeamResponse.of(
                progamer.getId(),
                progamer.getProgamerTag(),
                progamer.getName(),
                progamer.getBirth(),
                progamer.getPosition(),
                progamer.getLeagueWin(),
                progamer.getIntlWin(),
                progamer.getNationality(),
                teamMapper.toDto(recentTeam)
        );
    }
}
