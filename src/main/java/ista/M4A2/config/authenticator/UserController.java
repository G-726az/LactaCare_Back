package ista.M4A2.config.authenticator;

import ista.M4A2.models.entity.PersonaEmpleado;
import ista.M4A2.models.entity.PersonaPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired private PersonaPacienteRepository pacienteRepository;
    @Autowired private PersonaEmpleadoRepository empleadoRepository;

    // Endpoint para ver perfil (Datos solicitados: Img, Nombre, Apellido, Cedula, FechaNac, Rol)
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<PersonaPaciente> pOpt = pacienteRepository.findByCorreo(email);
        if (pOpt.isPresent()) {
            PersonaPaciente p = pOpt.get();
            // Retornamos DTO específico con los datos que pediste
            return ResponseEntity.ok(new UserProfileDto(
                p.getImagenPerfil(), 
                p.getPrimerNombre(), 
                p.getPrimerApellido(), // Apellido único (o primer apellido)
                p.getCedula(), 
                p.getFechaNacimiento().toString(), 
                "PACIENTE"
            ));
        }

        Optional<PersonaEmpleado> eOpt = empleadoRepository.findByCorreo(email);
        if (eOpt.isPresent()) {
            PersonaEmpleado e = eOpt.get();
            return ResponseEntity.ok(new UserProfileDto(
                e.getPerfilEmpleadoImg(), 
                e.getPrimerNombre(), 
                e.getPrimerApellido(), 
                e.getCedula(), 
                e.getFechaNacimiento().toString(), 
                e.getRol().getNombreRol()
            ));
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoint para editar perfil (SOLO Imagen y Primer Nombre)
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest request) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<PersonaPaciente> pOpt = pacienteRepository.findByCorreo(email);
        if (pOpt.isPresent()) {
            PersonaPaciente p = pOpt.get();
            if (request.getPrimerNombre() != null) p.setPrimerNombre(request.getPrimerNombre());
            if (request.getImagenPerfil() != null) p.setImagenPerfil(request.getImagenPerfil());
            pacienteRepository.save(p);
            return ResponseEntity.ok(new MessageResponse("Perfil actualizado correctamente"));
        }

        Optional<PersonaEmpleado> eOpt = empleadoRepository.findByCorreo(email);
        if (eOpt.isPresent()) {
            PersonaEmpleado e = eOpt.get();
            if (request.getPrimerNombre() != null) e.setPrimerNombre(request.getPrimerNombre());
            if (request.getImagenPerfil() != null) e.setPerfilEmpleadoImg(request.getImagenPerfil());
            empleadoRepository.save(e);
            return ResponseEntity.ok(new MessageResponse("Perfil actualizado correctamente"));
        }
        return ResponseEntity.notFound().build();
    }
}

// DTOs para este controller
class UserProfileDto {
    public String imagen;
    public String primerNombre;
    public String apellido;
    public String cedula;
    public String fechaNacimiento;
    public String rol;
    
    public UserProfileDto(String i, String n, String a, String c, String f, String r) {
        this.imagen = i; this.primerNombre = n; this.apellido = a; 
        this.cedula = c; this.fechaNacimiento = f; this.rol = r;
    }
}

class UpdateProfileRequest {
    private String primerNombre;
    private String imagenPerfil;
    
    // Getters y Setters
    public String getPrimerNombre() { return primerNombre; }
    public void setPrimerNombre(String primerNombre) { this.primerNombre = primerNombre; }
    public String getImagenPerfil() { return imagenPerfil; }
    public void setImagenPerfil(String imagenPerfil) { this.imagenPerfil = imagenPerfil; }
}
