package pq.progamerquiz.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pq.progamerquiz.domain.progamer.mapper.ProgamerMapper;
import pq.progamerquiz.domain.team.mapper.TeamMapper;
import pq.progamerquiz.domain.team.service.TeamQueryService;

@Configuration
@RequiredArgsConstructor
public class MapperConfig {

    private final TeamQueryService teamQueryService;
    private final TeamMapper teamMapper;

    @Bean
    public TeamMapper teamMapper() {
        return new TeamMapper();
    }

    @Bean
    public ProgamerMapper progamerMapper() {
        return new ProgamerMapper(teamQueryService, teamMapper);
    }
}
