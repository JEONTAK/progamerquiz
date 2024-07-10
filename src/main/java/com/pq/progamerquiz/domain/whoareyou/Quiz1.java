package com.pq.progamerquiz.domain.whoareyou;

import com.pq.progamerquiz.progamerinfo.ProGamer;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Entity
public class Quiz1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> answer;
}
