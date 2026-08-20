package th.ac.tu.circles.shared.client;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TuApiClient {

  private final RestTemplate restTemplate;
  private final String baseUrl;

  public TuApiClient(
      RestTemplateBuilder restTemplateBuilder,
      @Value("${tu.api.base-url}") String baseUrl,
      @Value("${tu.api.application-key}") String applicationKey) {
    // Optimization: Configure default Application-Key header on RestTemplateBuilder
    // to avoid allocating HttpHeaders and HttpEntity objects on every API request.
    this.restTemplate =
        restTemplateBuilder
            .defaultHeader("Application-Key", applicationKey)
            .setConnectTimeout(Duration.ofSeconds(3))
            .setReadTimeout(Duration.ofSeconds(3))
            .build();
    this.baseUrl = baseUrl;
  }

  public TuApiDtos.StudentProfileResponse getStudentProfile(String studentId) {
    String url = baseUrl + "/api/v1/profile/student?studentid=" + studentId;
    return restTemplate.getForObject(url, TuApiDtos.StudentProfileResponse.class);
  }

  public TuApiDtos.EmployeeProfileResponse getEmployeeProfile(String username) {
    String url = baseUrl + "/api/v1/profile/employee?username=" + username;
    return restTemplate.getForObject(url, TuApiDtos.EmployeeProfileResponse.class);
  }
}
