package th.ac.tu.circles.identity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import th.ac.tu.circles.identity.dto.IdentityDtos;
import th.ac.tu.circles.shared.client.TuApiClient;
import th.ac.tu.circles.shared.client.TuApiDtos;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
      "spring.datasource.url=jdbc:h2:mem:tu_circles;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_LOWER=TRUE",
      "spring.datasource.driver-class-name=org.h2.Driver",
      "spring.datasource.username=sa",
      "spring.datasource.password=",
      "spring.flyway.enabled=true"
    })
class IdentityIntegrationTest {

  @Autowired private TestRestTemplate restTemplate;

  @MockBean private TuApiClient tuApiClient;

  @Test
  void testVerifyAndGetIdentity() {
    TuApiDtos.StudentProfileResponse studentProfile =
        TuApiDtos.StudentProfileResponse.builder()
            .studentid("6409600002")
            .displaynameTh("สมหญิง รักเรียน")
            .displaynameEn("Somying Rakrian")
            .type("student")
            .statsid("REG999")
            .statusname("Normal")
            .levelName("Bachelor")
            .faculty("Engineering")
            .department("Computer Engineering")
            .build();

    given(tuApiClient.getStudentProfile("6409600002")).willReturn(studentProfile);

    IdentityDtos.VerificationRequest request =
        new IdentityDtos.VerificationRequest("student", "6409600002");

    ResponseEntity<IdentityDtos.UserIdentityDto> verifyResponse =
        restTemplate.postForEntity(
            "/api/v1/identity/verify", request, IdentityDtos.UserIdentityDto.class);

    assertThat(verifyResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(verifyResponse.getBody()).isNotNull();
    assertThat(verifyResponse.getBody().getIdentifier()).isEqualTo("6409600002");
    assertThat(verifyResponse.getBody().getDisplayNameTh()).isEqualTo("สมหญิง รักเรียน");

    ResponseEntity<IdentityDtos.UserIdentityDto> getResponse =
        restTemplate.getForEntity(
            "/api/v1/identity/6409600002", IdentityDtos.UserIdentityDto.class);

    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(getResponse.getBody()).isNotNull();
    assertThat(getResponse.getBody().getFacultyOrOrganization()).isEqualTo("Engineering");
  }
}
