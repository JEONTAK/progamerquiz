package pq.progamerquiz.domain.valorant.quizzes.igotyou.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/valorant/igotyou/start")
public class ValorantIGotYouRenderController {

    @GetMapping
    public String renderQuizPage() {
        return "quizzes/valorant/igotyou";
    }

}

