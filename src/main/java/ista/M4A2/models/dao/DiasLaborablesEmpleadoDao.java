package ista.M4A2.models.dao;

import ista.M4A2.models.entity.DiasLaborablesEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiasLaborablesEmpleadoDao extends JpaRepository<DiasLaborablesEmpleado, Long> {
}