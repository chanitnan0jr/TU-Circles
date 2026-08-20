package th.ac.tu.circles.shared.client;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TuApiClient {

  private final RestTemplate restTemplate;
  private final String baseUrl;
  private final String applicationKey;

  public TuApiClient(
      RestTemplateBuilder restTemplateBuilder,
      @Value("${tu.api.base-url}") String baseUrl,
      @Value("${tu.api.application-key}") String applicationKey) {
    this.restTemplate =
        restTemplateBuilder
            .setConnectTimeout(Duration.ofSeconds(3))
            .setReadTimeout(Duration.ofSeconds(3))
            .build();
    this.baseUrl = baseUrl;
    this.applicationKey = applicationKey;
  }

  public TuApiDtos.StudentProfileResponse getStudentProfile(String studentId) {
    String url = baseUrl + "/api/v1/profile/student?studentid=" + studentId;
    HttpHeaders headers = createHeaders();
    HttpEntity<Void> entity = new HttpEntity<>(headers);

    ResponseEntity<TuApiDtos.StudentProfileResponse> response =
        restTemplate.exchange(url, HttpMethod.GET, entity, TuApiDtos.StudentProfileResponse.class);
    return response.getBody();
  }

  public TuApiDtos.EmployeeProfileResponse getEmployeeProfile(String username) {
    String url = baseUrl + "/api/v1/profile/employee?username=" + username;
    HttpHeaders headers = createHeaders();
    HttpEntity<Void> entity = new HttpEntity<>(headers);

    ResponseEntity<TuApiDtos.EmployeeProfileResponse> response =
        restTemplate.exchange(url, HttpMethod.GET, entity, TuApiDtos.EmployeeProfileResponse.class);
    return response.getBody();
  }

  private HttpHeaders createHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Application-Key", applicationKey);
    return headers;
  }
}
