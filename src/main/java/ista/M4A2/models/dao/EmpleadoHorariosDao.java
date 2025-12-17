package ista.M4A2.models.dao;

import ista.M4A2.models.entity.EmpleadoHorarios;
import ista.M4A2.models.entity.EmpleadoHorariosPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoHorariosDao extends JpaRepository<EmpleadoHorarios, EmpleadoHorariosPK> {
    
    @Query("SELECT eh FROM EmpleadoHorarios eh WHERE eh.empleado.idPerEmpleado = :idEmpleado")
    List<EmpleadoHorarios> findByIdEmpleado(@Param("idEmpleado") Integer idEmpleado);
    
    @Query("SELECT eh FROM EmpleadoHorarios eh WHERE eh.horario.idHorario = :idHorario")
    List<EmpleadoHorarios> findByIdHorario(@Param("idHorario") Integer idHorario);
    
    @Query("SELECT COUNT(eh) > 0 FROM EmpleadoHorarios eh WHERE eh.empleado.idPerEmpleado = :idEmpleado AND eh.horario.idHorario = :idHorario")
    boolean existsByEmpleadoAndHorario(@Param("idEmpleado") Integer idEmpleado, @Param("idHorario") Integer idHorario);
}