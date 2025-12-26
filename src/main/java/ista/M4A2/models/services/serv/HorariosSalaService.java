package ista.M4A2.models.services.serv;

import ista.M4A2.models.entity.HorariosSala;
import java.util.List;

public interface HorariosSalaService {
    
    List<HorariosSala> obtenerTodos();
    
    HorariosSala obtenerPorId(Integer id);
    
    HorariosSala guardar(HorariosSala horario);
    
    HorariosSala actualizar(Integer id, HorariosSala horario);
    
    void eliminar(Integer id);
}