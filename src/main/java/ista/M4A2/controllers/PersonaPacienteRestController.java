package ista.M4A2.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ista.M4A2.dto.UsuarioResponse;
import ista.M4A2.models.entity.PersonaPaciente;
import ista.M4A2.models.services.serv.IPersonaPacienteService;
import ista.M4A2.verificaciones.PasswordValidator;

@RestController
@RequestMapping("/api")
public class PersonaPacienteRestController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IPersonaPacienteService personaPacienteService;
	
	@GetMapping("/pacientes")
	public List<PersonaPaciente> index() {
		return personaPacienteService.findAll();
	}
	
	// CORREGIDO: Agregamos ("id")
	@GetMapping("/pacientes/{id}")
	public PersonaPaciente show(@PathVariable("id") Long id) {
		return personaPacienteService.findById(id);
	}
	
	@PostMapping("/pacientes")
	public ResponseEntity<?> save(@RequestBody PersonaPaciente personaPaciente) {

	    String passwordError = PasswordValidator.validatePassword(personaPaciente.getPassword());
	    if (passwordError != null) {
	        return ResponseEntity
	                .badRequest()
	                .body(passwordError);
	    }

	    personaPaciente.setPassword(
	            passwordEncoder.encode(personaPaciente.getPassword())
	    );

	    PersonaPaciente saved = personaPacienteService.save(personaPaciente);

	    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	// NUEVO ENDPOINT SEGURO CON DTO
    @GetMapping("/pacientes/listar-dto")
    public ResponseEntity<List<UsuarioResponse>> listarPacientesDTO() {
        try {
            List<PersonaPaciente> pacientes = personaPacienteService.findAll();
            
            List<UsuarioResponse> respuesta = pacientes.stream()
                .map(p -> new UsuarioResponse(
                    p.getId(),
                    p.getCedula(),
                    p.getPrimerNombre() + " " + p.getPrimerApellido(),
                    p.getCorreo(),
                    p.getTelefono(),
                    "PACIENTE",
                    p.getImagenPerfil()
                ))
                .collect(Collectors.toList());
                
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // CORREGIDO: Agrege id para que lo pueda encontrar ("id")
	@PutMapping("/pacientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public PersonaPaciente update(@RequestBody PersonaPaciente personaPaciente, @PathVariable("id") Long id) {
		PersonaPaciente personaPacienteActual = personaPacienteService.findById(id);
		
		// Actualizar los campos
		personaPacienteActual.setCedula(personaPaciente.getCedula());
		personaPacienteActual.setImagenPerfil(personaPaciente.getImagenPerfil());
		personaPacienteActual.setPrimerNombre(personaPaciente.getPrimerNombre());
		personaPacienteActual.setSegundoNombre(personaPaciente.getSegundoNombre());
		personaPacienteActual.setPrimerApellido(personaPaciente.getPrimerApellido());
		personaPacienteActual.setSegundoApellido(personaPaciente.getSegundoApellido());
		personaPacienteActual.setFechaNacimiento(personaPaciente.getFechaNacimiento());
		personaPacienteActual.setCorreo(personaPaciente.getCorreo());
		personaPacienteActual.setTelefono(personaPaciente.getTelefono());
		personaPacienteActual.setDiscapacidad(personaPaciente.isDiscapacidad());
		
		// Validar y actualizar la contraseña si se proporciona una nueva
		if (personaPaciente.getPassword() != null && !personaPaciente.getPassword().isEmpty()) {
			String passwordError = PasswordValidator.validatePassword(personaPaciente.getPassword());
			if (passwordError == null) {
				personaPacienteActual.setPassword(passwordEncoder.encode(personaPaciente.getPassword()));
			}
		}
		
		// Guardar y retornar el paciente actualizado
		return personaPacienteService.save(personaPacienteActual);
	}
	
	// CORREGIDO: Agrege ("id") para que lo pueda encontrar
	@DeleteMapping("/pacientes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		personaPacienteService.delete(id);
	}
	
}