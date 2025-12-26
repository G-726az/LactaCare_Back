package ista.M4A2.models.services.serv;

import ista.M4A2.models.entity.DiasLaborablesSala;
import java.util.List;

public interface DiasLaborablesSalaService {
    
    List<DiasLaborablesSala> obtenerTodos();
    
    DiasLaborablesSala obtenerPorId(Long id);
    
    DiasLaborablesSala guardar(DiasLaborablesSala dias);
    
    DiasLaborablesSala actualizar(Long id, DiasLaborablesSala dias);
    
    void eliminar(Long id);
}