package pq.progamerquiz.progamer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}