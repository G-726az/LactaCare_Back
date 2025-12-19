package ista.M4A2.models.services.serv;

import ista.M4A2.models.entity.PersonaEmpleado;

import java.util.List;

public interface PersonaEmpleadoService {
    
    List<PersonaEmpleado> obtenerTodos();
    
    PersonaEmpleado obtenerPorId(Integer id);
    
    PersonaEmpleado obtenerPorCedula(String cedula);
    
    PersonaEmpleado obtenerPorCorreo(String correo);
    
    List<PersonaEmpleado> obtenerPorRol(Integer idRol);
    
    PersonaEmpleado guardar(PersonaEmpleado empleado);
    
    PersonaEmpleado actualizar(Integer id, PersonaEmpleado empleado);
    
    void eliminar(Integer id);
    
    PersonaEmpleado asignarHorario(Integer idEmpleado, Integer idHorario);
    
    PersonaEmpleado removerHorario(Integer idEmpleado, Integer idHorario);
    
    PersonaEmpleado findByResetToken(String token);
}