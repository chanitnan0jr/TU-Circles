package th.ac.tu.circles.identity.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_identity")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserIdentity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_type", nullable = false)
  private String userType;

  @Column(name = "identifier", nullable = false, unique = true)
  private String identifier;

  @Column(name = "display_name_th", nullable = false)
  private String displayNameTh;

  @Column(name = "display_name_en", nullable = false)
  private String displayNameEn;

  @Column(name = "status_or_emp_type", nullable = false)
  private String statusOrEmpType;

  @Column(name = "faculty_or_organization", nullable = false)
  private String facultyOrOrganization;

  @Column(name = "department")
  private String department;

  @Column(name = "level_name")
  private String levelName;

  @Column(name = "stats_id")
  private String statsId;

  @Column(name = "created_at")
  private OffsetDateTime createdAt;

  @Column(name = "updated_at")
  private OffsetDateTime updatedAt;
}
