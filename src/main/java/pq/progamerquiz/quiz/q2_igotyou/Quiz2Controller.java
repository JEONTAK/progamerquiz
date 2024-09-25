/*
package pq.progamerquiz.quiz.q2_igotyou;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pq.progamerquiz.quiz.q1_whoareyou.Quiz1Dto;

import java.util.ArrayList;
import java.util.List;

//Quiz : I Got you!
@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/igotyou")
public class Quiz2Controller{

    @Autowired
    private Quiz2Service quiz2Service;

    private Long totalCount;
    private Long correctCount;
    private List<Quiz2Dto> quizList = new ArrayList<>();


    @GetMapping
    public String startQuiz(Model model) {
        log.info("Make Answer Progamer");
        initialize();
        quizList = quiz2Service.getProgamers(totalCount);
        model.addAttribute("answer", answer);
        model.addAttribute("imagePath", imagePath);
        return "redirect:/whoareyou/" + answer.getId();
    }

    private void initialize() {
        totalCount = 0;
        correctCount = 0;
        quizList.clear();

    }

    @Transactional
    @GetMapping("/{progamerId}")
    public String showQuiz(@PathVariable Long progamerId, Model model, HttpSession session) {
        // 모델에 데이터 추가
        model.addAttribute("answer", answer);
        model.addAttribute("imagePath", imagePath);

        // 세션에서 정답 여부와 관련된 정보 가져오기
        Integer tryStatus = (Integer) session.getAttribute("try");
        List<Quiz1Dto> guessedList = (List<Quiz1Dto>) session.getAttribute("guessedList");
        Integer attempts = (Integer) session.getAttribute("attempts");
        model.addAttribute("try", tryStatus);
        model.addAttribute("guessedList", guessedList);
        model.addAttribute("attempts", attempts);
        model.addAttribute("maxAttempts", MAX_ATTEMPTS);

        log.info("Progamer: " + answer);  // progamer가 null인지 확인
        log.info("Image Path: " + imagePath);
        log.info("Try status: " + tryStatus);
        log.info("Guessed List: " + guessedList);
        log.info("Attempts: " + attempts);
        // 세션 정보 초기화
        session.removeAttribute("try");
        session.removeAttribute("guessedList");
        session.removeAttribute("attempts");

        return "quizzes/whoareyou";
    }
}
*/
