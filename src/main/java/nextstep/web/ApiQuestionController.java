package nextstep.web;

import nextstep.domain.Question;
import nextstep.domain.User;
import nextstep.dto.ListResponse;
import nextstep.security.LoginUser;
import nextstep.service.QnAService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class ApiQuestionController {
    private final QnAService qnaService;

    public ApiQuestionController(QnAService qnaService) {
        this.qnaService = qnaService;
    }

    @GetMapping("")
    public List<Question> list() {
        return qnaService.findAll();
    }

    @GetMapping("{id}")
    public Question detail(@PathVariable Long id) {
        return qnaService.findById(id);
    }

    @PostMapping("")
    public Question create(@LoginUser User loginUser, @RequestBody Question question) {
        return qnaService.create(loginUser, question);
    }
}
