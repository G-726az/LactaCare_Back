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

import ista.M4A2.models.entity.ContenedorLeche;
import ista.M4A2.models.services.serv.IContenedorLecheService;

@RestController
@RequestMapping("/api")
public class ContenedorLecheRestController {

	@Autowired
	private IContenedorLecheService contenedorLecheService;
	
	@GetMapping("/contenedores-leche")
	public List<ContenedorLeche> index() {
		return contenedorLecheService.findAll();
	}
	
	@GetMapping("/contenedores-leche/{id}")
	public ContenedorLeche show(@PathVariable Long id) {
		return contenedorLecheService.findById(id);
	}
	
	@PostMapping("/contenedores-leche")
	@ResponseStatus(HttpStatus.CREATED)
	public ContenedorLeche save(@RequestBody ContenedorLeche contenedorLeche) {
		return contenedorLecheService.save(contenedorLeche);
	}
	
	@PutMapping("/contenedores-leche/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ContenedorLeche update(@RequestBody ContenedorLeche contenedorLeche, @PathVariable Long id) {
		ContenedorLeche contenedorActual = contenedorLecheService.findById(id);
		contenedorActual.setFechaHoraExtraccion(contenedorLeche.getFechaHoraExtraccion());
		contenedorActual.setFechaHoraCaducidad(contenedorLeche.getFechaHoraCaducidad());
		contenedorActual.setCantidadMililitros(contenedorLeche.getCantidadMililitros());
		contenedorActual.setEstado(contenedorLeche.getEstado());
		contenedorActual.setAtencion(contenedorLeche.getAtencion());
		return contenedorLecheService.save(contenedorActual);
	}
	
	@DeleteMapping("/contenedores-leche/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		contenedorLecheService.delete(id);
	}
}
