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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pq.progamerquiz.quiz.q1_whoareyou.Quiz1Dto;

import java.util.ArrayList;
import java.util.List;

//Quiz : I Got you!
@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/igotyou")
public class Quiz2Controller {

    @Autowired
    private Quiz2Service quiz2Service;

    private int totalCount;
    private int correctCount;
    private List<Quiz2Dto> quizList = new ArrayList<>();

    private void initialize() {
        totalCount = 0;
        correctCount = 0;
        quizList.clear();
    }

    @GetMapping
    public String setQuiz(Model model) {
        log.info("Start Quiz2 I got You!");
        initialize();
        model.addAttribute("totalCount", totalCount);
        return "quizzes/igotyou";
    }

    @GetMapping("/{totalCount}")
    public String runningQuiz(Model model) {
        log.info("Set Progamers...");
        for (Quiz2Dto quiz2Dto : quizList) {
            log.info(quiz2Dto.getIndex() + " : " + quiz2Dto.getPid());
            for (int i = 0; i < quiz2Dto.getTeamNames().size(); i++) {
                log.info("    Team : " + quiz2Dto.getTeamNames().get(i) + " (" + quiz2Dto.getTeamYears().get(i) +")");
            }
        }
        model.addAttribute("quizList", quizList);
        model.addAttribute("correctCount", correctCount);
        model.addAttribute("totalCount", totalCount);
        return "quizzes/igotyou";
    }

    @PostMapping
    public String startQuiz(@RequestParam("totalCount") int count, Model model){
        log.info("Request size : " + count);
        totalCount = count;
        quizList = quiz2Service.getProgamers(totalCount);
        model.addAttribute("quizList", quizList);
        model.addAttribute("correctCount", correctCount);
        model.addAttribute("totalCount", totalCount);
        return "redirect:/igotyou/" + totalCount;
    }

}

