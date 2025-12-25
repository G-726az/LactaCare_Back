package ista.M4A2.config.authenticator;
<<<<<<< HEAD
<<<<<<< HEAD
import ista.M4A2.models.entity.PersonaEmpleado;
import ista.M4A2.models.entity.PersonaPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)

import ista.M4A2.models.entity.PersonaEmpleado;
import ista.M4A2.models.entity.PersonaPaciente;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
<<<<<<< HEAD
<<<<<<< HEAD
=======

>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======

>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

<<<<<<< HEAD
<<<<<<< HEAD
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
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
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
<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
    public String getImagenPerfil() { return imagenPerfil; }
    public void setImagenPerfil(String imagenPerfil) { this.imagenPerfil = imagenPerfil; }
}
