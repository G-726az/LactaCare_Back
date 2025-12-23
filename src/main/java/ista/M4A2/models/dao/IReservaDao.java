package ista.M4A2.models.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ista.M4A2.models.entity.Reserva;

@Repository
public interface IReservaDao extends JpaRepository<Reserva, Long> {

    List<Reserva> findByPersonaPacienteId(Long idPaciente);

    List<Reserva> findByFecha(LocalDate fecha);

    
    @Query("SELECT r FROM Reserva r WHERE r.salaLactancia.idLactario = :idSala AND r.fecha = :fecha")
    List<Reserva> findBySalaLactanciaIdAndFecha(@Param("idSala") Long idSala, @Param("fecha") LocalDate fecha);
}
