package support.test;

import nextstep.domain.User;
import nextstep.domain.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest extends BaseTest {
    private static final String DEFAULT_LOGIN_USER = "javajigi";

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private UserRepository userRepository;

    public TestRestTemplate template() {
        return template;
    }

    public TestRestTemplate basicAuthTemplate() {
        return basicAuthTemplate(defaultUser());
    }

    public TestRestTemplate basicAuthTemplate(User loginUser) {
        return template.withBasicAuth(loginUser.getUserId(), loginUser.getPassword());
    }

    protected User defaultUser() {
        return findByUserId(DEFAULT_LOGIN_USER);
    }

    protected User findByUserId(String userId) {
        return userRepository.findByUserId(userId).get();
    }

    protected <T> ResponseEntity<T> getResource(String location, Class<T> responseType) {
        ResponseEntity<T> response = template().getForEntity(location, responseType);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        return response;
    }

    protected <T> ResponseEntity<T> getResourceWithUser(String url, Class<T> responseType, User loginUser) {
        return basicAuthTemplate(loginUser).getForEntity(url, responseType);
    }

    protected <T> String createResource(String url, T bodyPayload) {
        ResponseEntity<String> response = template().postForEntity(url, bodyPayload, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        return response.getHeaders().getLocation().getPath();
    }

    protected <T> String createResourceWithUser(String url, T bodyPayload, User loginUser) {
        ResponseEntity<String> response = basicAuthTemplate(loginUser).postForEntity(url, bodyPayload, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        return response.getHeaders().getLocation().getPath();
    }
}
