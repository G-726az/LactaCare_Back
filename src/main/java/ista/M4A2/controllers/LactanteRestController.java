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

import ista.M4A2.models.entity.Lactante;
import ista.M4A2.models.services.serv.ILactanteService;

@RestController
@RequestMapping("/api")
public class LactanteRestController {
	
	@Autowired
	private ILactanteService lactanteService;
	
	@GetMapping("/lactantes")
	public List<Lactante> index() {
		return lactanteService.findAll();
	}
	
	@GetMapping("/lactantes/{id}")
	public Lactante show(@PathVariable Long id) {
		return lactanteService.findById(id);
	}
	
	@PostMapping("/lactantes")
	@ResponseStatus(HttpStatus.CREATED)
	public Lactante save(@RequestBody Lactante lactante) {
		return lactanteService.save(lactante);
	}
	
	@PutMapping("/lactantes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Lactante update(@RequestBody Lactante lactante, @PathVariable Long id) {
		Lactante lactanteActual = lactanteService.findById(id);
		lactanteActual.setNombre(lactante.getNombre());
		lactanteActual.setFechaNacimiento(lactante.getFechaNacimiento());
		lactanteActual.setGenero(lactante.getGenero());
		lactanteActual.setPersonaPaciente(lactante.getPersonaPaciente());
		return lactanteService.save(lactanteActual);
	}
	
	@DeleteMapping("/lactantes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		lactanteService.delete(id);
	}
}
