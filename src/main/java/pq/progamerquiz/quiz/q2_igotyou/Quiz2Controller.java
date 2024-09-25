package pq.progamerquiz.quiz.q2_igotyou;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
