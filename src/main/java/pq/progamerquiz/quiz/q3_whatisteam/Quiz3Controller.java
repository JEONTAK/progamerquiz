package pq.progamerquiz.quiz.q3_whatisteam;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pq.progamerquiz.progamer.Progamer;
import pq.progamerquiz.quiz.q2_igotyou.Quiz2Dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Quiz : Who are you?
@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/whatisteam")
public class Quiz3Controller {

    @Autowired
    private Quiz3Service quiz3Service;
    private List<Quiz3Dto> quizList = new ArrayList<>();
    private String isSubmitted;
    private String isCorrect;
    private final int idx = 15;
    private int currentIdx;

    private void initialize() {
        isSubmitted = "true";
        isCorrect = "true";
        currentIdx = 0;
        quizList.clear();
        quizList = quiz3Service.getTeams(idx);
    }

    @GetMapping
    public String startQuiz(Model model) {
        log.info("Start Quiz3 What is Team?");
        initialize();
        return "redirect:/whatisteam/start";
    }

    @GetMapping("/start")
    public String goQuiz(Model model) {
        log.info("Current : " + currentIdx + " Team Name : " + quizList.get(currentIdx).getTeamName());
        log.info("isSubmitted : " + isSubmitted + " isCorrect : " + isCorrect);
        model.addAttribute("quizList", quizList);
        model.addAttribute("index", idx);
        model.addAttribute("currentIdx", currentIdx);
        model.addAttribute("isCorrect", isCorrect);
        model.addAttribute("isSubmitted", isSubmitted);
        return "quizzes/whatisteam";
    }

    @PostMapping("/{teamName}")
    public String submitTeam(@RequestParam("teamName") String teamName, RedirectAttributes redirectAttributes) {
        if (quiz3Service.isExist(teamName)) {
            isSubmitted = "true";
            if (Objects.equals(quiz3Service.getTeamId(teamName), quizList.get(currentIdx).getTeamId())) {
                isCorrect = "true";
            }else{
                isCorrect = "false";
            }
            currentIdx++;
        }else{
            isCorrect = "false";
            isSubmitted = "false";
        }
        redirectAttributes.addFlashAttribute("currentIdx", currentIdx);
        redirectAttributes.addFlashAttribute("isCorrect", isCorrect);
        redirectAttributes.addFlashAttribute("isSubmitted", isSubmitted);
        return "redirect:/whatisteam/start";
    }
}
