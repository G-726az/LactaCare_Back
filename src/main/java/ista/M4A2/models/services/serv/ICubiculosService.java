package ista.M4A2.models.services.serv;

import java.util.List;
import ista.M4A2.models.entity.Cubiculos;

public interface ICubiculosService {
    
    List<Cubiculos> findAll();
    
    Cubiculos findById(Long id);
    
    Cubiculos save(Cubiculos cubiculo);
    
    void delete(Long id);
    
    List<Cubiculos> findBySalaLactanciaId(Long idLactario);
    
    List<Cubiculos> findByEstado(String estado);
    
    List<Cubiculos> findDisponiblesBySala(Long idLactario, String estado);
}