package ista.M4A2.models.dao;

import ista.M4A2.models.entity.Sala_Lactancia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface Sala_LactanciaDao extends JpaRepository<Sala_Lactancia, Long> {
    
    // Buscar solo salas activas
    List<Sala_Lactancia> findByEstado(String estado);
    
    // Buscar por ID y estado
    Optional<Sala_Lactancia> findByIdLactarioAndEstado(Long id, String estado);
    
    // Query personalizada para obtener activos
    @Query("SELECT s FROM Sala_Lactancia s WHERE s.estado = 'ACTIVO'")
    List<Sala_Lactancia> findAllActive();
}