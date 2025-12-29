package ista.M4A2.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ista.M4A2.models.dto.ReservaDTO;
import ista.M4A2.models.entity.Reserva;
import ista.M4A2.models.services.serv.IReservaService;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaRestController {
	
	@Autowired
	private IReservaService reservaService;
	
	// 1. LISTAR TODAS
	@GetMapping
	public ResponseEntity<List<Reserva>> index() {
		return ResponseEntity.ok(reservaService.findAll());
	}
	
	// 2. VER UNA ESPECÍFICA
	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable("id") Long id) {
		Reserva reserva = reservaService.findById(id);
		if (reserva == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(reserva);
	}

	// 3. CREAR RESERVA
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Reserva reserva) {
		try {
			Reserva nuevaReserva = reservaService.save(reserva);
			return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReserva);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	// 4. ACTUALIZAR
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody Reserva reserva, @PathVariable("id") Long id) {
		Reserva reservaActual = reservaService.findById(id);
		
		if (reservaActual == null) {
			return ResponseEntity.notFound().build();
		}

		try {
			reservaActual.setEstado(reserva.getEstado());
			reservaActual.setFecha(reserva.getFecha());
			reservaActual.setHoraInicio(reserva.getHoraInicio());
			reservaActual.setHoraFin(reserva.getHoraFin());
			reservaActual.setPersonaPaciente(reserva.getPersonaPaciente());
			reservaActual.setSalaLactancia(reserva.getSalaLactancia());
			
			Reserva actualizado = reservaService.save(reservaActual);
			return ResponseEntity.status(HttpStatus.CREATED).body(actualizado);
			
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
	// 5. BORRAR
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		reservaService.delete(id);
		return ResponseEntity.noContent().build();
	}

	// --- 6. PACIENTE: Ver SOLO mis reservas (AHORA USANDO DTO) ---
	@GetMapping("/paciente/{idPaciente}")
	public ResponseEntity<List<ReservaDTO>> obtenerPorPaciente(@PathVariable("idPaciente") Long idPaciente) {
		// CAMBIO CLAVE: Llamamos al método que ya devuelve los datos listos y "desconectados" de la BD
		return ResponseEntity.ok(reservaService.buscarReservasPorPacienteDTO(idPaciente));
	}

	// 7. DOCTOR: Ver agenda por fecha
	@GetMapping("/fecha/{fecha}")
	public ResponseEntity<List<Reserva>> obtenerPorFecha(
			@PathVariable("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
		return ResponseEntity.ok(reservaService.findByFecha(fecha));
	}
}
