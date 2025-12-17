package ista.M4A2.models.dao;

import ista.M4A2.models.entity.PersonaEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaEmpleadoDao extends JpaRepository<PersonaEmpleado, Integer> {
    
    Optional<PersonaEmpleado> findByCedula(String cedula);
    
    Optional<PersonaEmpleado> findByCorreo(String correo);
    
    List<PersonaEmpleado> findByRolIdRoles(Integer idRol);
}