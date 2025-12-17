package ista.M4A2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ista.M4A2.models.entity.Reserva;
import ista.M4A2.models.services.serv.IReservaService;

@RestController
@RequestMapping("/api")
public class ReservaRestController {
	
	@Autowired
	private IReservaService reservaService;
	
	@GetMapping("/reservas")
	public List<Reserva> index() {
		return reservaService.findAll();
	}
	
	@GetMapping("/reservas/{id}")
	public Reserva show(@PathVariable Long id) {
		return reservaService.findById(id);
	}
	
	@PostMapping("/reservas")
	@ResponseStatus(HttpStatus.CREATED)
	public Reserva save(@RequestBody Reserva reserva) {
		return reservaService.save(reserva);
	}
	
	@PutMapping("/reservas/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Reserva update(@RequestBody Reserva reserva, @PathVariable Long id) {
		Reserva reservaActual = reservaService.findById(id);
		reservaActual.setEstado(reserva.getEstado());
		reservaActual.setFecha(reserva.getFecha());
		reservaActual.setHoraInicio(reserva.getHoraInicio());
		reservaActual.setHoraFin(reserva.getHoraFin());
		reservaActual.setPaciente(reserva.getPaciente());
		return reservaService.save(reservaActual);
	}
	
	@DeleteMapping("/reservas/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		reservaService.delete(id);
	}
}
