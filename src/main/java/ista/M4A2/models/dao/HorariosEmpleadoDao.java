package ista.M4A2.models.dao;

import ista.M4A2.models.entity.HorariosEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorariosEmpleadoDao extends JpaRepository<HorariosEmpleado, Integer> {
}