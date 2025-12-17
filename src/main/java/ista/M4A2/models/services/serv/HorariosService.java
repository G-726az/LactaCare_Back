package ista.M4A2.models.services.serv;

import ista.M4A2.models.entity.Horarios;

import java.util.List;

public interface HorariosService {
    
    List<Horarios> obtenerTodos();
    
    Horarios obtenerPorId(Integer id);
    
    Horarios guardar(Horarios horario);
    
    Horarios actualizar(Integer id, Horarios horario);
    
    void eliminar(Integer id);
}