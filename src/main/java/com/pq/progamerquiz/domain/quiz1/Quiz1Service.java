package com.pq.progamerquiz.domain.quiz1;


import com.pq.progamerquiz.progamerInfo.Progamer;
import com.pq.progamerquiz.progamerInfo.ProgamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class Quiz1Service {

    @Autowired
    private ProgamerRepository proGamerRepository;

    public Progamer getRandomProGamer() {
        List<Progamer> allGamers = proGamerRepository.findAll();
        Random random = new Random();
        return allGamers.get(random.nextInt(allGamers.size()));
    }


    public Progamer getProGamer(String idOrName) {
        if (proGamerRepository.findByName(idOrName).equals(idOrName)) {
            return proGamerRepository.findByName(idOrName);
        } else if (proGamerRepository.findByPid(idOrName).equals(idOrName)) {
            return proGamerRepository.findByPid(idOrName);
        }
        return null;
    }
}
