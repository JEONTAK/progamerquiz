package pq.progamerquiz.progamer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pq.progamerquiz.team.Team;

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
    private List<Team> teams;


    public ProgamerDto(Long id, String pid, String name) {
        this.id = id;
        this.pid = pid;
        this.name = name;
    }

}