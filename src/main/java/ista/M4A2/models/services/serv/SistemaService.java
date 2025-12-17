package ista.M4A2.models.services.serv;

import ista.M4A2.models.entity.Sistema;

import java.util.List;

public interface SistemaService {
    
    List<Sistema> obtenerTodos();
    
    Sistema obtenerPorId(Integer id);
    
    Sistema guardar(Sistema sistema);
    
    Sistema actualizar(Integer id, Sistema sistema);
    
    void eliminar(Integer id);
}