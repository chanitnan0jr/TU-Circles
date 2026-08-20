package th.ac.tu.circles.identity.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import th.ac.tu.circles.identity.dto.IdentityDtos;
import th.ac.tu.circles.identity.service.IdentityService;

@RestController
@RequestMapping("/api/v1/identity")
public class IdentityController {

  private final IdentityService identityService;

  public IdentityController(IdentityService identityService) {
    this.identityService = identityService;
  }

  @PostMapping("/verify")
  public ResponseEntity<IdentityDtos.UserIdentityDto> verify(
      @Valid @RequestBody IdentityDtos.VerificationRequest request) {
    IdentityDtos.UserIdentityDto dto = identityService.verifyAndSave(request);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/{identifier}")
  public ResponseEntity<IdentityDtos.UserIdentityDto> getByIdentifier(
      @PathVariable String identifier) {
    IdentityDtos.UserIdentityDto dto = identityService.getByIdentifier(identifier);
    return ResponseEntity.ok(dto);
  }
}
