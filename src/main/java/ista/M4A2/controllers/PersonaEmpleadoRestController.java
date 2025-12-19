package ista.M4A2.controllers;

import ista.M4A2.models.entity.PersonaEmpleado;
import ista.M4A2.models.services.serv.PersonaEmpleadoService;
import ista.M4A2.verificaciones.PasswordValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class PersonaEmpleadoRestController {
    
    @Autowired
    private PersonaEmpleadoService empleadoService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping
    public ResponseEntity<List<PersonaEmpleado>> obtenerTodos() {
        try {
            List<PersonaEmpleado> empleados = empleadoService.obtenerTodos();
            return ResponseEntity.ok(empleados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PersonaEmpleado> obtenerPorId(@PathVariable Integer id) {
        try {
            PersonaEmpleado empleado = empleadoService.obtenerPorId(id);
            return ResponseEntity.ok(empleado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<PersonaEmpleado> obtenerPorCedula(@PathVariable String cedula) {
        try {
            PersonaEmpleado empleado = empleadoService.obtenerPorCedula(cedula);
            return ResponseEntity.ok(empleado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/correo/{correo}")
    public ResponseEntity<PersonaEmpleado> obtenerPorCorreo(@PathVariable String correo) {
        try {
            PersonaEmpleado empleado = empleadoService.obtenerPorCorreo(correo);
            return ResponseEntity.ok(empleado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/rol/{idRol}")
    public ResponseEntity<List<PersonaEmpleado>> obtenerPorRol(@PathVariable Integer idRol) {
        try {
            List<PersonaEmpleado> empleados = empleadoService.obtenerPorRol(idRol);
            return ResponseEntity.ok(empleados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody PersonaEmpleado empleado) {
        try {
            // Validar contraseña si se proporciona
            if (empleado.getPassword() != null && !empleado.getPassword().isEmpty()) {
                String passwordError = PasswordValidator.validatePassword(empleado.getPassword());
                if (passwordError != null) {
                    return ResponseEntity.badRequest().body(passwordError);
                }
                // Encriptar contraseña
                empleado.setPassword(passwordEncoder.encode(empleado.getPassword()));
            }
            
            PersonaEmpleado nuevoEmpleado = empleadoService.guardar(empleado);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEmpleado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody PersonaEmpleado empleado) {
        try {
            PersonaEmpleado empleadoExistente = empleadoService.obtenerPorId(id);
            
            // Actualizar campos básicos
            empleadoExistente.setPerfilEmpleadoImg(empleado.getPerfilEmpleadoImg());
            empleadoExistente.setCedula(empleado.getCedula());
            empleadoExistente.setPrimerNombre(empleado.getPrimerNombre());
            empleadoExistente.setSegundoNombre(empleado.getSegundoNombre());
            empleadoExistente.setPrimerApellido(empleado.getPrimerApellido());
            empleadoExistente.setSegundoApellido(empleado.getSegundoApellido());
            empleadoExistente.setCorreo(empleado.getCorreo());
            empleadoExistente.setTelefono(empleado.getTelefono());
            empleadoExistente.setFechaNacimiento(empleado.getFechaNacimiento());
            empleadoExistente.setRol(empleado.getRol());
            
            // Actualizar contraseña si se proporciona una nueva
            if (empleado.getPassword() != null && !empleado.getPassword().isEmpty()) {
                String passwordError = PasswordValidator.validatePassword(empleado.getPassword());
                if (passwordError != null) {
                    return ResponseEntity.badRequest().body(passwordError);
                }
                empleadoExistente.setPassword(passwordEncoder.encode(empleado.getPassword()));
            }
            
            PersonaEmpleado empleadoActualizado = empleadoService.guardar(empleadoExistente);
            return ResponseEntity.ok(empleadoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            empleadoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/{idEmpleado}/horarios/{idHorario}")
    public ResponseEntity<PersonaEmpleado> asignarHorario(
            @PathVariable Integer idEmpleado, 
            @PathVariable Integer idHorario) {
        try {
            PersonaEmpleado empleado = empleadoService.asignarHorario(idEmpleado, idHorario);
            return ResponseEntity.ok(empleado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{idEmpleado}/horarios/{idHorario}")
    public ResponseEntity<PersonaEmpleado> removerHorario(
            @PathVariable Integer idEmpleado, 
            @PathVariable Integer idHorario) {
        try {
            PersonaEmpleado empleado = empleadoService.removerHorario(idEmpleado, idHorario);
            return ResponseEntity.ok(empleado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}