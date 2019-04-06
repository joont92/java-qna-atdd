package nextstep.web;

import nextstep.domain.Question;
import nextstep.domain.QuestionBody;
import nextstep.domain.User;
import nextstep.dto.ListResponse;
import nextstep.security.LoginUser;
import nextstep.service.QnAService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@Controller
@RequestMapping("/api/questions")
public class ApiQnAController {
    private final QnAService qnaService;

    public ApiQnAController(QnAService qnaService) {
        this.qnaService = qnaService;
    }

    @GetMapping("")
    public ResponseEntity<ListResponse<Question>> list() {
        List<Question> list = qnaService.findAll();

        return ResponseEntity.ok(new ListResponse<>(list));
    }

    @GetMapping("{id}")
    public ResponseEntity<Question> detail(@PathVariable Long id) {
        return ResponseEntity.ok(qnaService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<Void> create(@LoginUser User loginUser, @RequestBody QuestionBody payload) {
        Question question = qnaService.createQuestion(loginUser, payload);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(question.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Question> update(@LoginUser User loginUser, @PathVariable Long id, @RequestBody QuestionBody newPayload) {
        Question question = qnaService.updateQuestion(id, loginUser, newPayload);

        return ResponseEntity.ok(question);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@LoginUser User loginUser, @PathVariable Long id) {
        qnaService.deleteQuestion(id, loginUser);

        return ResponseEntity.ok().build();
    }
}