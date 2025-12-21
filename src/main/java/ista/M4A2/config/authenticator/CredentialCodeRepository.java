package ista.M4A2.config.authenticator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CredentialCodeRepository extends JpaRepository<CredentialCode, Integer> {
    
    Optional<CredentialCode> findByCodeAndIsActive(String code, Boolean isActive);
    Optional<CredentialCode> findByCode(String code);
}
