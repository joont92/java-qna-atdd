package nextstep.web;

import nextstep.domain.Question;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import java.util.List;

public class ApiQuestionAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiQuestionAcceptanceTest.class);

    @Test
    public void 질문_목록을_조회한다() {
        ResponseEntity<List<Question>> response = template().exchange(
            "/api/questions", HttpMethod.GET,
            null, new ParameterizedTypeReference<List<Question>>() {});

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void 질문_상세를_조회한다() {
        ResponseEntity<Question> response = getResource(String.format("/api/questions/%d", 1), Question.class);
    }

    @Test
    public void 질문을_등록한다() {
        Question question = new Question("This is title", "This is contents");
        String location = createResourceWithUser("/api/questions", question, defaultUser());

        getResource(location, Question.class);
    }
}
