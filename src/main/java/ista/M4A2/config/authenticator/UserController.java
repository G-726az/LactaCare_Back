package ista.M4A2.config.authenticator;
import ista.M4A2.models.entity.PersonaEmpleado;
import ista.M4A2.models.entity.PersonaPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
    private PersonaPacienteRepository pacienteRepository;

    @Autowired
    private PersonaEmpleadoRepository empleadoRepository;

    /**
     * GET /api/user/me
     * Retorna la info del usuario autenticado
     */
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        // Obtenemos el correo del SecurityContext (seteado en JwtAuthenticationFilter)
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Buscar en pacientes
        Optional<PersonaPaciente> pacienteOpt = pacienteRepository.findByCorreo(email);
        if (pacienteOpt.isPresent()) {
            PersonaPaciente p = pacienteOpt.get();
            return ResponseEntity.ok(new UserInfoResponse(
                p.getId(),
                p.getCorreo(),
                p.getPrimerNombre() + " " + p.getPrimerApellido(),
                "PACIENTE",
                p.getAuthProvider().name(),
                p.getImagenPerfil(),
                p.getProfileCompleted()
            ));
        }

        // Buscar en empleados
        Optional<PersonaEmpleado> empleadoOpt = empleadoRepository.findByCorreo(email);
        if (empleadoOpt.isPresent()) {
            PersonaEmpleado e = empleadoOpt.get();
            return ResponseEntity.ok(new UserInfoResponse(
                e.getIdPerEmpleado().longValue(),
                e.getCorreo(),
                e.getPrimerNombre() + " " + e.getPrimerApellido(),
                e.getRol().getNombreRol(),
                e.getAuthProvider().name(),
                e.getPerfilEmpleadoImg(),
                e.getProfileCompleted()
            ));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * PUT /api/user/profile
     * Actualiza datos básicos del perfil desde Android
     */
    @PutMapping("/profile")
    public ResponseEntity<?> actualizarPerfil(@RequestBody ActualizarPerfilRequest request) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<PersonaPaciente> pacienteOpt = pacienteRepository.findByCorreo(email);
        if (pacienteOpt.isPresent()) {
            PersonaPaciente p = pacienteOpt.get();
            if (request.getTelefono() != null) p.setTelefono(request.getTelefono());
            if (request.getImagenPerfil() != null) p.setImagenPerfil(request.getImagenPerfil());
            pacienteRepository.save(p);
            return ResponseEntity.ok(new MessageResponse("Perfil de paciente actualizado", "SUCCESS"));
        }

        Optional<PersonaEmpleado> empleadoOpt = empleadoRepository.findByCorreo(email);
        if (empleadoOpt.isPresent()) {
            PersonaEmpleado e = empleadoOpt.get();
            if (request.getTelefono() != null) e.setTelefono(request.getTelefono());
            if (request.getImagenPerfil() != null) e.setPerfilEmpleadoImg(request.getImagenPerfil());
            empleadoRepository.save(e);
            return ResponseEntity.ok(new MessageResponse("Perfil de empleado actualizado", "SUCCESS"));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

/**
 * DTO para recibir la actualización del perfil
 */
class ActualizarPerfilRequest {
    private String telefono;
    private String imagenPerfil;
    // Getters y Setters
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getImagenPerfil() { return imagenPerfil; }
    public void setImagenPerfil(String imagenPerfil) { this.imagenPerfil = imagenPerfil; }
}
