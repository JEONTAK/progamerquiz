package pq.progamerquiz.progamer;

import jakarta.persistence.EntityManager;
import pq.progamerquiz.team.Team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        // teams 필드를 쉼표로 구분하여 리스트로 변환
        List<Team> teams = new ArrayList<>();
        if (progamerDto.getTeams() != null) {
            List<Long> teamIds = Arrays.stream(progamerDto.getTeams().split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            // 데이터베이스에서 팀 ID로 Team 엔티티를 가져옴
            teams = teamIds.stream()
                    .map(id -> em.find(Team.class, id))
                    .collect(Collectors.toList());

        }

        progamer.setTeams(teams);
        return progamer;
    }
}
