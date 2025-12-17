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

import ista.M4A2.models.entity.ContactoEmergencia;
import ista.M4A2.models.services.serv.IContactoEmergenciaService;

@RestController
@RequestMapping("/api")
public class ContactoEmergenciaRestController {
	
	@Autowired
	private IContactoEmergenciaService contactoEmergenciaService;
	
	@GetMapping("/contactos-emergencia")
	public List<ContactoEmergencia> index() {
		return contactoEmergenciaService.findAll();
	}
	
	@GetMapping("/contactos-emergencia/{id}")
	public ContactoEmergencia show(@PathVariable Long id) {
		return contactoEmergenciaService.findById(id);
	}
	
	@PostMapping("/contactos-emergencia")
	@ResponseStatus(HttpStatus.CREATED)
	public ContactoEmergencia save(@RequestBody ContactoEmergencia contactoEmergencia) {
		return contactoEmergenciaService.save(contactoEmergencia);
	}
	
	@PutMapping("/contactos-emergencia/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ContactoEmergencia update(@RequestBody ContactoEmergencia contactoEmergencia, @PathVariable Long id) {
		ContactoEmergencia contactoEmergenciaActual = contactoEmergenciaService.findById(id);
		contactoEmergenciaActual.setNombre(contactoEmergencia.getNombre());
		contactoEmergenciaActual.setTelefono(contactoEmergencia.getTelefono());
		contactoEmergenciaActual.setPersonaPaciente(contactoEmergencia.getPersonaPaciente());
		return contactoEmergenciaService.save(contactoEmergenciaActual);
	}
	
	@DeleteMapping("/contactos-emergencia/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		contactoEmergenciaService.delete(id);
	}
}
