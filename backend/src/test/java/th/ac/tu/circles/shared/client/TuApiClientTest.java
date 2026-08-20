package th.ac.tu.circles.shared.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestClientException;

class TuApiClientTest {

  private static WireMockServer wireMockServer;
  private TuApiClient tuApiClient;

  @BeforeAll
  static void startWireMock() {
    wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().dynamicPort());
    wireMockServer.start();
  }

  @AfterAll
  static void stopWireMock() {
    wireMockServer.stop();
  }

  @BeforeEach
  void setUp() {
    wireMockServer.resetAll();
    tuApiClient =
        new TuApiClient(new RestTemplateBuilder(), wireMockServer.baseUrl(), "test-app-key-12345");
  }

  @Test
  void getStudentProfile_Success() {
    wireMockServer.stubFor(
        get(urlEqualTo("/api/v1/profile/student?studentid=6409600001"))
            .withHeader("Application-Key", equalTo("test-app-key-12345"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "studentid": "6409600001",
                          "displayname_th": "สมชาย ดีใจ",
                          "displayname_en": "Somchai Deejai",
                          "type": "student",
                          "statsid": "REG123",
                          "statusname": "Normal",
                          "level_name": "Bachelor",
                          "faculty": "Science",
                          "department": "Computer Science"
                        }
                        """)));

    TuApiDtos.StudentProfileResponse response = tuApiClient.getStudentProfile("6409600001");

    assertThat(response).isNotNull();
    assertThat(response.getStudentid()).isEqualTo("6409600001");
    assertThat(response.getDisplaynameTh()).isEqualTo("สมชาย ดีใจ");
    assertThat(response.getFaculty()).isEqualTo("Science");
  }

  @Test
  void getEmployeeProfile_Success() {
    wireMockServer.stubFor(
        get(urlEqualTo("/api/v1/profile/employee?username=emp001"))
            .withHeader("Application-Key", equalTo("test-app-key-12345"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                        {
                          "username": "emp001",
                          "displayname_th": "อ.สมศรี มีสุข",
                          "displayname_en": "Somsri Meesook",
                          "type": "employee",
                          "employee_type": "Academic",
                          "organization": "Faculty of Science"
                        }
                        """)));

    TuApiDtos.EmployeeProfileResponse response = tuApiClient.getEmployeeProfile("emp001");

    assertThat(response).isNotNull();
    assertThat(response.getUsername()).isEqualTo("emp001");
    assertThat(response.getOrganization()).isEqualTo("Faculty of Science");
  }

  @Test
  void getStudentProfile_Timeout_ThrowsException() {
    wireMockServer.stubFor(
        get(urlEqualTo("/api/v1/profile/student?studentid=6409600001"))
            .willReturn(aResponse().withFixedDelay(4000)));

    assertThatThrownBy(() -> tuApiClient.getStudentProfile("6409600001"))
        .isInstanceOf(RestClientException.class);
  }

  @Test
  void getStudentProfile_MalformedJson_ThrowsException() {
    wireMockServer.stubFor(
        get(urlEqualTo("/api/v1/profile/student?studentid=6409600001"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{ malformed json ... }")));

    assertThatThrownBy(() -> tuApiClient.getStudentProfile("6409600001"))
        .isInstanceOf(RestClientException.class);
  }
}
