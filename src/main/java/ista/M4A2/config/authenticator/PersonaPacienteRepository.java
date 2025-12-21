package ista.M4A2.config.authenticator;

import ista.M4A2.models.entity.PersonaPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PersonaPacienteRepository extends JpaRepository<PersonaPaciente, Long> {
    
    // Métodos existentes...
    Optional<PersonaPaciente> findByCorreo(String correo);
    Optional<PersonaPaciente> findByCedula(String cedula);
    
    // AGREGAR ESTOS:
    Optional<PersonaPaciente> findByGoogleId(String googleId);
    boolean existsByCorreo(String correo);
    boolean existsByCedula(String cedula);
    boolean existsByGoogleId(String googleId);
}
