package com.pq.progamerquiz.domain.whoareyou;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class Quiz1Repository {

    private static final Map<Long, Quiz1> store = new HashMap<>(); // static
    private static long sequence = 0L; //static

}
