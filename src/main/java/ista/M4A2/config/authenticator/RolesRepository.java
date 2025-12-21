package ista.M4A2.config.authenticator;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ista.M4A2.models.entity.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByNombreRol(String nombreRol);
    boolean existsByNombreRol(String nombreRol);

}
