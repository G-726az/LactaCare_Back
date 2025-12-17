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

import ista.M4A2.models.entity.Atencion;
import ista.M4A2.models.services.serv.IAtencionService;

@RestController
@RequestMapping("/api")
public class AtencionRestController {
	
	@Autowired
	private IAtencionService atencionService;
	
	@GetMapping("/atenciones")
	public List<Atencion> index() {
		return atencionService.findAll();
	}
	
	@GetMapping("/atenciones/{id}")
	public Atencion show(@PathVariable Long id) {
		return atencionService.findById(id);
	}
	
	@PostMapping("/atenciones")
	@ResponseStatus(HttpStatus.CREATED)
	public Atencion save(@RequestBody Atencion atencion) {
		return atencionService.save(atencion);
	}
	
	@PutMapping("/atenciones/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Atencion update(@RequestBody Atencion atencion, @PathVariable Long id) {
		Atencion atencionActual = atencionService.findById(id);
		atencionActual.setFecha(atencion.getFecha());
		atencionActual.setHora(atencion.getHora());
		atencionActual.setReserva(atencion.getReserva());
		return atencionService.save(atencionActual);
	}
	
	@DeleteMapping("/atenciones/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		atencionService.delete(id);
	}
}
