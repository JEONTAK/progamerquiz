package pq.progamerquiz.quiz.q3_whatisteam;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;

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
        isCorrect = "start";
        currentIdx = 0;
        quizList.clear();
        quizList = quiz3Service.getTeams(idx);
    }

    @GetMapping
    public String startQuiz(Model model) {
        log.info("Start Quiz3 What is Team?");
        initialize();
        return "redirect:/whatisteam/quiz";
    }

    @GetMapping("/quiz")
    public String goQuiz(Model model) {
        log.info("Current : " + currentIdx + " |Team Id" + quizList.get(currentIdx).getTeamId());
        log.info("Team Name : " + quizList.get(currentIdx).getTeamName() + " |Year" + quizList.get(currentIdx).getTeamYear());
        log.info("isSubmitted : " + isSubmitted + " isCorrect : " + isCorrect);
        model.addAttribute("quizList", quizList);
        model.addAttribute("index", idx);
        model.addAttribute("currentIdx", currentIdx);
        model.addAttribute("isCorrect", isCorrect);
        model.addAttribute("isSubmitted", isSubmitted);
        return "quizzes/whatisteam";
    }

    @PostMapping("/quiz")
    public String submitTeam(@RequestParam("input") String team, RedirectAttributes redirectAttributes) {
        String[] teamInfo =  team.split("/");
        if (teamInfo.length != 2) {
            isCorrect = "false";
            isSubmitted = "false";
        }else{
            String teamName = teamInfo[0];
            Long seasonYear = Long.parseLong(teamInfo[1]);
            log.info("Team Name : " + teamName + " Season Year : " + seasonYear);
            if (quiz3Service.isExist(teamName)) {
                isSubmitted = "true";
                if (quiz3Service.isAnswer(teamName, seasonYear, quizList.get(currentIdx))) {
                    isCorrect = "true";
                } else {
                    isCorrect = "false";
                }
                currentIdx++;
            } else {
                isCorrect = "false";
                isSubmitted = "false";
            }
        }
        redirectAttributes.addFlashAttribute("currentIdx", currentIdx);
        redirectAttributes.addFlashAttribute("isCorrect", isCorrect);
        redirectAttributes.addFlashAttribute("isSubmitted", isSubmitted);
        return "redirect:/whatisteam/quiz";
    }

}
