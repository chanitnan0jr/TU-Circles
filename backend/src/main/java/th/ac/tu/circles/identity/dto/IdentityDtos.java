package th.ac.tu.circles.identity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class IdentityDtos {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UserIdentityDto {
    private Long id;
    private String userType;
    private String identifier;
    private String displayNameTh;
    private String displayNameEn;
    private String statusOrEmpType;
    private String facultyOrOrganization;
    private String department;
    private String levelName;
    private String statsId;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class VerificationRequest {
    private String userType; // "student" or "employee"
    private String identifier; // studentid or username
  }
}
