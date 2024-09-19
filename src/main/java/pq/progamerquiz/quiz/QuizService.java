package pq.progamerquiz.quiz;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import pq.progamerquiz.quiz.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuizService {

    @Autowired
    private final QuizRepository quizRepository;
    @Autowired
    private EntityManager em;


    public void saveQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    public Quiz findQuizByUrl(String quizUrl) {
        return quizRepository.findQuizByUrl(quizUrl);
    }
}
