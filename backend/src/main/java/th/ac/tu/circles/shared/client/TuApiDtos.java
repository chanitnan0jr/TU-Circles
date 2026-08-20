package th.ac.tu.circles.shared.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TuApiDtos {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class StudentProfileResponse {
    @NotBlank private String studentid;

    @NotBlank
    @JsonProperty("displayname_th")
    private String displaynameTh;

    @NotBlank
    @JsonProperty("displayname_en")
    private String displaynameEn;

    @NotBlank private String type;

    @NotBlank private String statsid;

    @NotBlank private String statusname;

    @NotBlank
    @JsonProperty("level_name")
    private String levelName;

    @NotBlank private String faculty;

    @NotBlank private String department;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class EmployeeProfileResponse {
    @NotBlank private String username;

    @NotBlank
    @JsonProperty("displayname_th")
    private String displaynameTh;

    @NotBlank
    @JsonProperty("displayname_en")
    private String displaynameEn;

    @NotBlank private String type;

    @NotBlank
    @JsonProperty("employee_type")
    private String employeeType;

    @NotBlank private String organization;
  }
}
