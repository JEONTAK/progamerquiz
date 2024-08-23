package pq.progamerquiz.service;


import pq.progamerquiz.repository.ProgamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Quiz1Service {

    @Autowired
    private ProgamerRepository proGamerRepository;

}
