package ista.M4A2.models.services.serv;

import java.time.LocalDate;
import java.util.List;

import ista.M4A2.dto.ReservaDTO; // <--- Importar DTO
import ista.M4A2.models.entity.Reserva;

public interface IReservaService {

	public List<Reserva> findAll();
	
	public Reserva save(Reserva reserva);
	
	public Reserva findById(Long id);
	
	public void delete(Long id);
	
	// Métodos existentes
	public List<Reserva> findByPacienteId(Long idPaciente);
	public List<Reserva> findByFecha(LocalDate fecha);

    // --- AGREGA ESTE NUEVO ---
	public List<ReservaDTO> buscarReservasPorPacienteDTO(Long idPaciente);
}