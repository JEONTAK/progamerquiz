package pq.progamerquiz.domain.progamer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pq.progamerquiz.domain.progamer.entity.Progamer;
import pq.progamerquiz.domain.team.dto.TeamDto;
import pq.progamerquiz.domain.team.dto.response.TeamInfoResponse;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ProgamerResponse {

    private final Long id;
    private final String pid;
    private final String name;
    private final LocalDate birth;
    private final String position;
    private final Long league_win;
    private final Long intl_win;
    private final String nationality;
    private final TeamInfoResponse teamInfoResponse;

    public ProgamerResponse(Long id, String pid, String name, LocalDate birth, String position, Long league_win, Long intl_win, String nationality, List<TeamDto> teams) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.birth = birth;
        this.position = position;
        this.league_win = league_win;
        this.intl_win = intl_win;
        this.nationality = nationality;
        this.teams = teams;
    }

    public ProgamerResponse(Long id, String pid, String name) {
        this.id = id;
        this.pid = pid;
        this.name = name;
    }

    public ProgamerResponse(Long id, String pid, String name, LocalDate birth, String position, Long league_win, Long intl_win,
                            String nationality) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.birth = birth;
        this.position = position;
        this.league_win = league_win;
        this.intl_win = intl_win;
        this.nationality = nationality;
    }

    public static ProgamerResponse toDto(Progamer progamer) {
        if (progamer == null) {
            return null;
        }
        return new ProgamerResponse(
                progamer.getId(), progamer.getProgamerTag(), progamer.getName(), progamer.getBirth(),
                progamer.getPosition().toString(), progamer.getLeague_win(),
                progamer.getIntl_win(), progamer.getNationality(), TeamDto.listToDto(progamer.getTeams())
        );
    }

    public static ProgamerResponse toDtoNotUsingTeam(Progamer progamer) {
        if (progamer == null) {
            return null;
        }
        return new ProgamerResponse(
                progamer.getId(), progamer.getProgamerTag(), progamer.getName(), progamer.getBirth(),
                progamer.getPosition().toString(), progamer.getLeague_win(),
                progamer.getIntl_win(), progamer.getNationality(), null
        );
    }
}