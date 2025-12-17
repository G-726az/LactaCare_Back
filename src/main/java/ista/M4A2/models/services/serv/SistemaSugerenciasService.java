package ista.M4A2.models.services.serv;

import ista.M4A2.models.entity.SistemaSugerencias;
import java.util.List;

public interface SistemaSugerenciasService {
    
    List<SistemaSugerencias> obtenerTodos();
    
    SistemaSugerencias obtenerPorId(Integer id);
    
    SistemaSugerencias guardar(SistemaSugerencias sugerencia);
    
    SistemaSugerencias actualizar(Integer id, SistemaSugerencias sugerencia);
    
    void eliminar(Integer id);
}