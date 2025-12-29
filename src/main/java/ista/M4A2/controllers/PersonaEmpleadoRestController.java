package ista.M4A2.controllers;

import ista.M4A2.models.dto.PersonaEmpleadoDTO;
import ista.M4A2.models.entity.PersonaEmpleado;
import ista.M4A2.models.services.serv.PersonaEmpleadoService;
import ista.M4A2.verificaciones.PasswordValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class PersonaEmpleadoRestController {
    
    @Autowired
    private PersonaEmpleadoService empleadoService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping
    public ResponseEntity<List<PersonaEmpleadoDTO>> obtenerTodos() {
        try {
            List<PersonaEmpleado> empleados = empleadoService.obtenerTodos();
            List<PersonaEmpleadoDTO> empleadosDTO = empleados.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(empleadosDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PersonaEmpleadoDTO> obtenerPorId(@PathVariable Integer id) {
        try {
            PersonaEmpleado empleado = empleadoService.obtenerPorId(id);
            return ResponseEntity.ok(convertToDTO(empleado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<PersonaEmpleadoDTO> obtenerPorCedula(@PathVariable String cedula) {
        try {
            PersonaEmpleado empleado = empleadoService.obtenerPorCedula(cedula);
            return ResponseEntity.ok(convertToDTO(empleado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/correo/{correo}")
    public ResponseEntity<PersonaEmpleadoDTO> obtenerPorCorreo(@PathVariable String correo) {
        try {
            PersonaEmpleado empleado = empleadoService.obtenerPorCorreo(correo);
            return ResponseEntity.ok(convertToDTO(empleado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/rol/{idRol}")
    public ResponseEntity<List<PersonaEmpleadoDTO>> obtenerPorRol(@PathVariable Integer idRol) {
        try {
            List<PersonaEmpleado> empleados = empleadoService.obtenerPorRol(idRol);
            List<PersonaEmpleadoDTO> empleadosDTO = empleados.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(empleadosDTO);
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
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(nuevoEmpleado));
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
            
            // Actualizar nuevas relaciones
            if (empleado.getSalaLactancia() != null) {
                empleadoExistente.setSalaLactancia(empleado.getSalaLactancia());
            }
            if (empleado.getHorarioEmpleado() != null) {
                empleadoExistente.setHorarioEmpleado(empleado.getHorarioEmpleado());
            }
            if (empleado.getDiasLaborablesEmpleado() != null) {
                empleadoExistente.setDiasLaborablesEmpleado(empleado.getDiasLaborablesEmpleado());
            }
            
            // Actualizar contraseña si se proporciona una nueva
            if (empleado.getPassword() != null && !empleado.getPassword().isEmpty()) {
                String passwordError = PasswordValidator.validatePassword(empleado.getPassword());
                if (passwordError != null) {
                    return ResponseEntity.badRequest().body(passwordError);
                }
                empleadoExistente.setPassword(passwordEncoder.encode(empleado.getPassword()));
            }
            
            PersonaEmpleado empleadoActualizado = empleadoService.guardar(empleadoExistente);
            return ResponseEntity.ok(convertToDTO(empleadoActualizado));
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
<<<<<<< HEAD
=======
    
    /**
     * Método auxiliar para convertir PersonaEmpleado a DTO
     */
    private PersonaEmpleadoDTO convertToDTO(PersonaEmpleado empleado) {
        PersonaEmpleadoDTO dto = new PersonaEmpleadoDTO();
        dto.setIdPerEmpleado(empleado.getIdPerEmpleado());
        dto.setPerfilEmpleadoImg(empleado.getPerfilEmpleadoImg());
        dto.setCedula(empleado.getCedula());
        dto.setPrimerNombre(empleado.getPrimerNombre());
        dto.setSegundoNombre(empleado.getSegundoNombre());
        dto.setPrimerApellido(empleado.getPrimerApellido());
        dto.setSegundoApellido(empleado.getSegundoApellido());
        dto.setCorreo(empleado.getCorreo());
        dto.setTelefono(empleado.getTelefono());
        dto.setFechaNacimiento(empleado.getFechaNacimiento());
        
        // Convertir rol a DTO (sin la colección de empleados)
        if (empleado.getRol() != null) {
            PersonaEmpleadoDTO.RolDTO rolDTO = new PersonaEmpleadoDTO.RolDTO();
            rolDTO.setIdRol(empleado.getRol().getIdRoles());  // ⭐ Corregido: getIdRoles()
            rolDTO.setNombreRol(empleado.getRol().getNombreRol());
            dto.setRol(rolDTO);
        }
        
        // IDs de relaciones opcionales
        if (empleado.getSalaLactancia() != null) {
            // ⭐ Corregido: getIdLactario() y convertir Long a Integer
            dto.setSalaLactanciaId(empleado.getSalaLactancia().getIdLactario().intValue());
        }
        if (empleado.getHorarioEmpleado() != null) {
            dto.setHorarioEmpleadoId(empleado.getHorarioEmpleado().getIdHorarioEmpleado());
        }
        
        return dto;
    }
>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
}