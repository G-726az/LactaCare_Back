package ista.M4A2.models.services.serv;

import ista.M4A2.models.entity.EmpleadoHorarios;
import ista.M4A2.models.entity.EmpleadoHorariosPK;

import java.util.List;

public interface EmpleadoHorariosService {
    
    List<EmpleadoHorarios> obtenerTodos();
    
    EmpleadoHorarios obtenerPorId(EmpleadoHorariosPK id);
    
    List<EmpleadoHorarios> obtenerPorEmpleado(Integer idEmpleado);
    
    List<EmpleadoHorarios> obtenerPorHorario(Integer idHorario);
    
    boolean existeAsignacion(Integer idEmpleado, Integer idHorario);
    
    EmpleadoHorarios asignar(Integer idEmpleado, Integer idHorario);
    
    void eliminar(EmpleadoHorariosPK id);
    
    void eliminarAsignacion(Integer idEmpleado, Integer idHorario);
}