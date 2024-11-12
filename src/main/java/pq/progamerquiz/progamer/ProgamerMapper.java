package pq.progamerquiz.progamer;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import pq.progamerquiz.team.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class ProgamerMapper {

    public static Progamer toEntity(ProgamerDto progamerDto, EntityManager em) {
        Progamer progamer = new Progamer();
        progamer.setId(progamerDto.getId());
        progamer.setPid(progamerDto.getPid());
        progamer.setName(progamerDto.getName());
        progamer.setBirth(progamerDto.getBirth());
        progamer.setPosition(Progamer.Position.valueOf(progamerDto.getPosition()));
        progamer.setLeague_win(progamerDto.getLeague_win());
        progamer.setIntl_win(progamerDto.getIntl_win());
        progamer.setNationality(progamerDto.getNationality());
        progamer.setTeams(progamerDto.getTeams());

        return progamer;
    }
}
