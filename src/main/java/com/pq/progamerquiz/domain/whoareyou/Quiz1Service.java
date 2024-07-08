package com.pq.progamerquiz.domain.whoareyou;


import com.pq.progamerquiz.progamerinfo.ProGamer;
import com.pq.progamerquiz.progamerinfo.ProGamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class Quiz1Service {

    @Autowired
    private ProGamerRepository proGamerRepository;

    public ProGamer getRandomProGamer() {
        List<ProGamer> allGamers = proGamerRepository.findAll();
        Random random = new Random();
        return allGamers.get(random.nextInt(allGamers.size()));
    }


    public ProGamer getProGamer(String idOrName) {
        if (proGamerRepository.findByName(idOrName).equals(idOrName)) {
            return proGamerRepository.findByName(idOrName);
        }
        else
            return proGamerRepository.findByPlayerId(idOrName);
    }
}
