package com.pq.progamerquiz.domain.whoareyou;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class QuizRepository {

    private static final Map<Long, Quiz> store = new HashMap<>(); // static
    private static long sequence = 0L; //static

}
