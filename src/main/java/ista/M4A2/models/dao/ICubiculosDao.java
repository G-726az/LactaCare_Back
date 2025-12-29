package ista.M4A2.models.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ista.M4A2.models.entity.Cubiculos;

public interface ICubiculosDao extends JpaRepository<Cubiculos, Long> {
    
    // Buscar cubículos por sala de lactancia
    @Query("SELECT c FROM Cubiculos c WHERE c.salaLactancia.idLactario = :idLactario")
    List<Cubiculos> findBySalaLactanciaId(@Param("idLactario") Long idLactario);
    
    // Buscar cubículos por estado
    List<Cubiculos> findByEstadoCb(String estadoCb);
    
    // Buscar cubículos disponibles en una sala específica
    @Query("SELECT c FROM Cubiculos c WHERE c.salaLactancia.idLactario = :idLactario AND c.estadoCb = :estado")
    List<Cubiculos> findDisponiblesBySala(@Param("idLactario") Long idLactario, @Param("estado") String estado);
}