package th.ac.tu.circles.identity.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import th.ac.tu.circles.identity.domain.UserIdentity;

public interface UserIdentityRepository extends JpaRepository<UserIdentity, Long> {
  Optional<UserIdentity> findByIdentifier(String identifier);
}
