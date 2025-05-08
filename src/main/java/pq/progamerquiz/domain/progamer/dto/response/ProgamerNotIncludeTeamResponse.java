package pq.progamerquiz.domain.progamer.dto.response;

import lombok.Getter;
import pq.progamerquiz.common.enums.Position;
import pq.progamerquiz.domain.team.dto.response.TeamInfoResponse;

import java.time.LocalDate;

@Getter
public class ProgamerNotIncludeTeamResponse {

    private final Long id;
    private final String pid;
    private final String name;
    private final LocalDate birth;
    private final Position position;
    private final Long leagueWin;
    private final Long intlWin;
    private final String nationality;
    private final TeamInfoResponse recentTeam;

    private ProgamerNotIncludeTeamResponse(Long id, String pid, String name, LocalDate birth, Position position, Long leagueWin, Long intlWin, String nationality, TeamInfoResponse recentTeam) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.birth = birth;
        this.position = position;
        this.leagueWin = leagueWin;
        this.intlWin = intlWin;
        this.nationality = nationality;
        this.recentTeam = recentTeam;
    }

    public static ProgamerNotIncludeTeamResponse of(Long id, String pid, String name, LocalDate birth, Position position, Long league_win, Long intl_win, String nationality, TeamInfoResponse recentTeam) {
        return new ProgamerNotIncludeTeamResponse(id, pid, name, birth, position, league_win, intl_win, nationality, recentTeam);
    }
}