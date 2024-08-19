package com.pq.progamerquiz.domain.quiz1;

import com.pq.progamerquiz.progamerInfo.Progamer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Quiz1 {


    private Progamer answer;

    private List<Progamer> attemptList = new ArrayList<>();

    // 기본 생성자 (JPA 용)
    protected Quiz1() {
    }

    // 무작위 프로게이머를 answer로 설정하는 생성자
    public Quiz1(List<Progamer> progamers) {
        if (progamers == null || progamers.isEmpty()) {
            throw new IllegalArgumentException("프로게이머 목록이 비어있습니다.");
        }
        Random random = new Random();
        this.answer = progamers.get(random.nextInt(progamers.size()));
    }

    // attemptList에 프로게이머 추가
    public void addAttempt(Progamer progamer) {
        this.attemptList.add(progamer);
    }

}

