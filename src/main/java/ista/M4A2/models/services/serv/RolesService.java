package ista.M4A2.models.services.serv;

import ista.M4A2.models.entity.Roles;

import java.util.List;

public interface RolesService {
    
    List<Roles> obtenerTodos();
    
    Roles obtenerPorId(Integer id);
    
    Roles obtenerPorNombre(String nombreRol);
    
    Roles guardar(Roles rol);
    
    Roles actualizar(Integer id, Roles rol);
    
    void eliminar(Integer id);
}