package pq.progamerquiz.domain.progamer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pq.progamerquiz.domain.progamer.entity.Progamer;
import pq.progamerquiz.domain.team.dto.TeamDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgamerDto {

    private Long id;
    private String pid;
    private String name;
    private Long birth;
    private String position;
    private Long league_win;
    private Long intl_win;
    private String nationality;
    private List<TeamDto> teams;


    public ProgamerDto(Long id, String pid, String name) {
        this.id = id;
        this.pid = pid;
        this.name = name;
    }

    public ProgamerDto(Long id, String pid, String name, Long birth, String position, Long league_win, Long intl_win,
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

    public static ProgamerDto toDto(Progamer progamer) {
        if (progamer == null) {
            return null;
        }
        return new ProgamerDto(
                progamer.getId(), progamer.getPid(), progamer.getName(), progamer.getBirth(),
                progamer.getPosition().toString(), progamer.getLeague_win(),
                progamer.getIntl_win(), progamer.getNationality(), TeamDto.listToDto(progamer.getTeams())
        );
    }

    public static ProgamerDto toDtoNotUsingTeam(Progamer progamer) {
        if (progamer == null) {
            return null;
        }
        return new ProgamerDto(
                progamer.getId(), progamer.getPid(), progamer.getName(), progamer.getBirth(),
                progamer.getPosition().toString(), progamer.getLeague_win(),
                progamer.getIntl_win(), progamer.getNationality(), null
        );
    }
}