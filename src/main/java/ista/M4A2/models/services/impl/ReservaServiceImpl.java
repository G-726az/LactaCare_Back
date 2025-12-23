package ista.M4A2.models.services.impl;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors; // <--- Importante para las listas

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ista.M4A2.models.dao.IReservaDao;
import ista.M4A2.dto.ReservaDTO;
import ista.M4A2.models.entity.Reserva;
import ista.M4A2.models.services.serv.IReservaService;

@Service
public class ReservaServiceImpl implements IReservaService {

    @Autowired
    private IReservaDao reservaDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Reserva> findAll() {
        return reservaDao.findAll();
    }

    @Override
    @Transactional
    public Reserva save(Reserva reserva) {
        // --- 1. VALIDACIÓN DE HORARIO ---
        // Buscamos todas las reservas de ESA sala en ESE día
    	List<Reserva> reservasDelDia = reservaDao.findBySalaLactanciaIdAndFecha(
    	        reserva.getSalaLactancia().getIdLactario(),
    	        reserva.getFecha()
    	);

        // Revisamos una por una si chocan las horas
        for (Reserva existente : reservasDelDia) {
            // Ignoramos la misma reserva si estamos editando
            if (existente.getId().equals(reserva.getId())) continue;
            
            // Ignoramos reservas canceladas
            if ("CANCELADA".equalsIgnoreCase(existente.getEstado())) continue;

            // Lógica de Solapamiento
            if (reserva.getHoraInicio().isBefore(existente.getHoraFin()) &&
                reserva.getHoraFin().isAfter(existente.getHoraInicio())) {
                
                throw new RuntimeException("ERROR: La sala ya está ocupada en ese horario (" + 
                        existente.getHoraInicio() + " - " + existente.getHoraFin() + ")");
            }
        }

        // --- 2. SI PASA LA VALIDACIÓN, GUARDAMOS ---
        if (reserva.getEstado() == null) {
            reserva.setEstado("PENDIENTE");
        }
        
        return reservaDao.save(reserva);
    }

    @Override
    @Transactional(readOnly = true)
    public Reserva findById(Long id) {
        return reservaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        reservaDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reserva> findByPacienteId(Long idPaciente) {
        return reservaDao.findByPersonaPacienteId(idPaciente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reserva> findByFecha(java.time.LocalDate fecha) {
        return reservaDao.findByFecha(fecha);
    }

    // ==========================================
    // === NUEVA LÓGICA CON DTOs (SOLUCIÓN) ===
    // ==========================================

    // Método privado para convertir Entidad -> DTO
    private ReservaDTO convertirADTO(Reserva reserva) {
        ReservaDTO dto = new ReservaDTO();
        
        // Datos básicos
        dto.setIdReserva(reserva.getId());
        dto.setEstado(reserva.getEstado());
        dto.setFecha(reserva.getFecha());
        dto.setHoraInicio(reserva.getHoraInicio());
        dto.setHoraFin(reserva.getHoraFin());

        // Aplanamos Paciente (Solo sacamos lo necesario)
        if (reserva.getPersonaPaciente() != null) {
            dto.setIdPaciente(reserva.getPersonaPaciente().getId());
            dto.setNombrePaciente(reserva.getPersonaPaciente().getPrimerNombre());
            dto.setApellidoPaciente(reserva.getPersonaPaciente().getPrimerApellido());
        }

        // Aplanamos Sala (Solo sacamos lo necesario)
        if (reserva.getSalaLactancia() != null) {
            dto.setIdSala(reserva.getSalaLactancia().getIdLactario());
            dto.setNombreSala(reserva.getSalaLactancia().getNombreCMedico());
            
            // Aplanamos Institución
            if (reserva.getSalaLactancia().getInstitucion() != null) {
                dto.setNombreInstitucion(reserva.getSalaLactancia().getInstitucion().getNombreInstitucion());
            }
        }

        return dto;
    }

    // Implementación del método que devuelve DTOs
    @Override
    @Transactional(readOnly = true)
    public List<ReservaDTO> buscarReservasPorPacienteDTO(Long idPaciente) {
        // 1. Buscamos las entidades (Aquí la sesión de BD está abierta)
        List<Reserva> reservas = reservaDao.findByPersonaPacienteId(idPaciente);
        
        // 2. Convertimos a DTO antes de cerrar la transacción
        return reservas.stream()
                .map(this::convertirADTO) // Llamamos al método de arriba por cada reserva
                .collect(Collectors.toList());
    }
}