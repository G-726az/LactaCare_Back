package ista.M4A2.models.services.serv;

import ista.M4A2.models.entity.DiasLaborablesEmpleado;
import java.util.List;

public interface DiasLaborablesEmpleadoService {
    
    List<DiasLaborablesEmpleado> obtenerTodos();
    
    DiasLaborablesEmpleado obtenerPorId(Long id);
    
    DiasLaborablesEmpleado guardar(DiasLaborablesEmpleado dias);
    
    DiasLaborablesEmpleado actualizar(Long id, DiasLaborablesEmpleado dias);
    
    void eliminar(Long id);
}