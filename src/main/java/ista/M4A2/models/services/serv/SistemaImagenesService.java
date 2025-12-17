package ista.M4A2.models.services.serv;

import ista.M4A2.models.entity.SistemaImagenes;
import java.util.List;

public interface SistemaImagenesService {
    
    List<SistemaImagenes> obtenerTodos();
    
    SistemaImagenes obtenerPorId(Integer id);
    
    SistemaImagenes guardar(SistemaImagenes imagen);
    
    SistemaImagenes actualizar(Integer id, SistemaImagenes imagen);
    
    void eliminar(Integer id);
}