package pq.progamerquiz.domain.progamer.mapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pq.progamerquiz.domain.progamer.dto.response.ProgamerWithRecentTeamResponse;
import pq.progamerquiz.domain.progamer.entity.Progamer;
import pq.progamerquiz.domain.team.entity.Team;
import pq.progamerquiz.domain.team.mapper.TeamMapper;
import pq.progamerquiz.domain.team.service.TeamQueryService;

@Getter
@RequiredArgsConstructor
@Component
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

    public ProgamerWithRecentTeamResponse toDto(Progamer progamer){
        Team recentTeam = teamQueryService.findRecentTeamByProgamer(progamer.getId());
        return ProgamerWithRecentTeamResponse.of(
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
