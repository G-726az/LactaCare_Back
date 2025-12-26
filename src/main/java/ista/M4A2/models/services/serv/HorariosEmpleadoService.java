package ista.M4A2.models.services.serv;

import ista.M4A2.models.entity.HorariosEmpleado;
import java.util.List;

public interface HorariosEmpleadoService {
    
    List<HorariosEmpleado> obtenerTodos();
    
    HorariosEmpleado obtenerPorId(Integer id);
    
    HorariosEmpleado guardar(HorariosEmpleado horario);
    
    HorariosEmpleado actualizar(Integer id, HorariosEmpleado horario);
    
    void eliminar(Integer id);
}