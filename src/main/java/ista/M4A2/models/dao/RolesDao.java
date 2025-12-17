package ista.M4A2.models.dao;

import ista.M4A2.models.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesDao extends JpaRepository<Roles, Integer> {
    
    Optional<Roles> findByNombreRol(String nombreRol);
}