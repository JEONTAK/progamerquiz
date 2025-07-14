package pq.progamerquiz.domain.leagueoflegends.quiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pq.progamerquiz.domain.leagueoflegends.quiz.repository.QuizRepository;
import pq.progamerquiz.domain.leagueoflegends.quiz.entity.Quiz;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;


    public void saveQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

}
