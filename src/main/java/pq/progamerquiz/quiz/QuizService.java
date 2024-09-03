package pq.progamerquiz.quiz;

import pq.progamerquiz.quiz.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
}
