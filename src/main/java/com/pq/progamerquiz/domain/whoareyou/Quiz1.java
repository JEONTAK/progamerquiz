package com.pq.progamerquiz.domain.whoareyou;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Quiz1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> answer;
}
