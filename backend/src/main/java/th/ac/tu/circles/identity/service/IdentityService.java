package th.ac.tu.circles.identity.service;

import java.time.OffsetDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import th.ac.tu.circles.identity.domain.UserIdentity;
import th.ac.tu.circles.identity.dto.IdentityDtos;
import th.ac.tu.circles.identity.repository.UserIdentityRepository;
import th.ac.tu.circles.shared.client.TuApiClient;
import th.ac.tu.circles.shared.client.TuApiDtos;

@Service
public class IdentityService {

  private final UserIdentityRepository repository;
  private final TuApiClient tuApiClient;

  public IdentityService(UserIdentityRepository repository, TuApiClient tuApiClient) {
    this.repository = repository;
    this.tuApiClient = tuApiClient;
  }

  @Transactional
  public IdentityDtos.UserIdentityDto verifyAndSave(IdentityDtos.VerificationRequest request) {
    if ("student".equalsIgnoreCase(request.getUserType())) {
      TuApiDtos.StudentProfileResponse profile =
          tuApiClient.getStudentProfile(request.getIdentifier());
      UserIdentity entity =
          repository
              .findByIdentifier(profile.getStudentid())
              .orElse(UserIdentity.builder().createdAt(OffsetDateTime.now()).build());

      entity.setUserType("student");
      entity.setIdentifier(profile.getStudentid());
      entity.setDisplayNameTh(profile.getDisplaynameTh());
      entity.setDisplayNameEn(profile.getDisplaynameEn());
      entity.setStatusOrEmpType(profile.getStatusname());
      entity.setFacultyOrOrganization(profile.getFaculty());
      entity.setDepartment(profile.getDepartment());
      entity.setLevelName(profile.getLevelName());
      entity.setStatsId(profile.getStatsid());
      entity.setUpdatedAt(OffsetDateTime.now());

      UserIdentity saved = repository.save(entity);
      return mapToDto(saved);
    } else if ("employee".equalsIgnoreCase(request.getUserType())) {
      TuApiDtos.EmployeeProfileResponse profile =
          tuApiClient.getEmployeeProfile(request.getIdentifier());
      UserIdentity entity =
          repository
              .findByIdentifier(profile.getUsername())
              .orElse(UserIdentity.builder().createdAt(OffsetDateTime.now()).build());

      entity.setUserType("employee");
      entity.setIdentifier(profile.getUsername());
      entity.setDisplayNameTh(profile.getDisplaynameTh());
      entity.setDisplayNameEn(profile.getDisplaynameEn());
      entity.setStatusOrEmpType(profile.getEmployeeType());
      entity.setFacultyOrOrganization(profile.getOrganization());
      entity.setUpdatedAt(OffsetDateTime.now());

      UserIdentity saved = repository.save(entity);
      return mapToDto(saved);
    } else {
      throw new IllegalArgumentException("Invalid user type: " + request.getUserType());
    }
  }

  @Transactional(readOnly = true)
  public IdentityDtos.UserIdentityDto getByIdentifier(String identifier) {
    UserIdentity entity =
        repository
            .findByIdentifier(identifier)
            .orElseThrow(() -> new RuntimeException("User identity not found for: " + identifier));
    return mapToDto(entity);
  }

  private IdentityDtos.UserIdentityDto mapToDto(UserIdentity entity) {
    return IdentityDtos.UserIdentityDto.builder()
        .id(entity.getId())
        .userType(entity.getUserType())
        .identifier(entity.getIdentifier())
        .displayNameTh(entity.getDisplayNameTh())
        .displayNameEn(entity.getDisplayNameEn())
        .statusOrEmpType(entity.getStatusOrEmpType())
        .facultyOrOrganization(entity.getFacultyOrOrganization())
        .department(entity.getDepartment())
        .levelName(entity.getLevelName())
        .statsId(entity.getStatsId())
        .build();
  }
}
