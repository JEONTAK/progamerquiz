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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pq.progamerquiz.progamer.Progamer;
import pq.progamerquiz.quiz.q1_whoareyou.Quiz1Dto;
import pq.progamerquiz.quiz.q1_whoareyou.Quiz1Service;

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
    private List<Quiz2Dto> quizList = new ArrayList<>();

    private void initialize() {
        totalCount = 0;
        quizList.clear();
    }

    @GetMapping
    public String startQuiz(Model model) {
        initialize();
        return "redirect:/igotyou/start";
    }

    @GetMapping("/start")
    public String goQuiz(Model model) {
        log.info("Start Quiz2 I got You!");
        model.addAttribute("quizList", quizList);
        model.addAttribute("totalCount", totalCount);
        return "quizzes/igotyou";
    }


    @GetMapping("/{totalCount}")
    public String settingQuiz(RedirectAttributes redirectAttributes) {
        log.info("Start Quiz2 I got You!");
        for (Quiz2Dto quiz2Dto : quizList) {
            log.info(quiz2Dto.getIndex() + " : " + quiz2Dto.getPid());
            for (int i = 0; i < quiz2Dto.getTeamNames().size(); i++) {
                log.info("    Team : " + quiz2Dto.getTeamNames().get(i) + " (" + quiz2Dto.getTeamYears().get(i) +")");
            }
        }
        redirectAttributes.addFlashAttribute("quizList", quizList);
        redirectAttributes.addFlashAttribute("totalCount", totalCount);

        return "redirect:/igotyou/start";
    }

    @PostMapping("/{totalCount}")
    public String setQuiz(@RequestParam("totalCount") int count, RedirectAttributes redirectAttributes) {
        log.info("Request size : " + count);
        log.info("Set Progamers...");
        initialize();
        totalCount = count;
        quizList = quiz2Service.getProgamers(totalCount);

        // 세션에 데이터를 저장
        redirectAttributes.addFlashAttribute("quizList", quizList);
        redirectAttributes.addFlashAttribute("totalCount", totalCount);

        return "redirect:/igotyou/" + totalCount;
    }

}

