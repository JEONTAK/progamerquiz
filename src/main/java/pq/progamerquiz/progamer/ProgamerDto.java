package pq.progamerquiz.progamer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String teams;
    private Long latestTeam;
    private String latestLeague;

    public ProgamerDto(Long id, String pid, String name, Long birth, String position, Long league_win, Long intl_win, String nationality , Long latestTeam, String latestLeague) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.birth = birth;
        this.position = position;
        this.league_win = league_win;
        this.intl_win = intl_win;
        this.nationality = nationality;
        this.teams = teams;
        this.latestTeam = latestTeam;
        this.latestLeague = latestLeague;
    }
}