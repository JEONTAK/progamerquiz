package com.pq.progamerquiz.domain.whoareyou;


import com.pq.progamerquiz.progamerinfo.ProGamer;
import com.pq.progamerquiz.progamerinfo.ProGamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

@Service
public class QuizService {

    @Autowired
    DataSource datasource;

    public void Connect() throws SQLException {
        Connection connection = datasource.getConnection();
        System.out.println(connection.getMetaData().getURL());
        System.out.println(connection.getMetaData().getUserName());
    }

    @Autowired
    private ProGamerRepository proGamerRepository;

    public ProGamer getRandomProGamer() {
        List<ProGamer> allGamers = proGamerRepository.findAll();
        Random random = new Random();
        return allGamers.get(random.nextInt(allGamers.size()));
    }

    public ProGamer getProGamerByPlayerId(String playerId) {
        return proGamerRepository.findByPlayerId(playerId);
    }
}
