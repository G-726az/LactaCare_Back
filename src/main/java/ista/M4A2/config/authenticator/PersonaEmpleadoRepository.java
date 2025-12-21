package ista.M4A2.config.authenticator;

import ista.M4A2.models.entity.PersonaEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaEmpleadoRepository extends JpaRepository<PersonaEmpleado, Integer> {
    
    // AGREGAR ESTOS:
    Optional<PersonaEmpleado> findByCorreo(String correo);
    Optional<PersonaEmpleado> findByCedula(String cedula);
    Optional<PersonaEmpleado> findByGoogleId(String googleId);
    boolean existsByCorreo(String correo);
    boolean existsByCedula(String cedula);
    boolean existsByGoogleId(String googleId);
    
 // Método útil para buscar por rol (si lo necesitas)
    List<PersonaEmpleado> findByRol_IdRoles(Integer idRol);
}