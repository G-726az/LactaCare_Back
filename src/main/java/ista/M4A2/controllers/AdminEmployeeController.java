package ista.M4A2.controllers;

import ista.M4A2.models.entity.PersonaEmpleado;
import ista.M4A2.models.entity.Roles;
import ista.M4A2.models.services.serv.PersonaEmpleadoService;
import ista.M4A2.models.services.serv.RolesService;
import jakarta.validation.Valid;
import ista.M4A2.config.authenticator.JwtTokenProvider;
import ista.M4A2.config.authenticator.PersonaEmpleadoRepository;
import ista.M4A2.config.authenticator.PersonaPacienteRepository; // Importante para validar cruce
import ista.M4A2.dto.CambiarPasswordInicialRequest;
import ista.M4A2.dto.CrearEmpleadoRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;
import ista.M4A2.dto.UsuarioResponse;

@RestController
@RequestMapping("/api/admin/empleados")
@CrossOrigin(origins = "*")
public class AdminEmployeeController {

    @Autowired
    private PersonaEmpleadoService empleadoService;
    @Autowired
    private PersonaEmpleadoRepository empleadoRepository;
    @Autowired
    private PersonaPacienteRepository pacienteRepository;
    @Autowired
    private RolesService rolesService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;

    // AGREGA ESTA INYECCIÓN para poder generar el token al finalizar
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // --- Endpoint 1: Crear Empleado (Solo Admin) ---
    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> crearEmpleado(@Valid @RequestBody CrearEmpleadoRequest request) {
        try {
            // 1. Validar unicidad
            if (empleadoRepository.existsByCorreo(request.getCorreo()) ||
                    pacienteRepository.existsByCorreo(request.getCorreo())) {
                return ResponseEntity.badRequest().body(Map.of("message", "El correo ya está registrado."));
            }
            if (empleadoRepository.existsByCedula(request.getCedula()) ||
                    pacienteRepository.existsByCedula(request.getCedula())) {
                return ResponseEntity.badRequest().body(Map.of("message", "La cédula ya está registrada."));
            }

            // 2. Generar contraseña temporal
            String tempPassword = generarPasswordTemporal();

            // 3. Crear entidad
            PersonaEmpleado nuevo = new PersonaEmpleado();
            nuevo.setCedula(request.getCedula());
            nuevo.setPrimerNombre(request.getPrimerNombre());
            nuevo.setSegundoNombre(request.getSegundoNombre());
            nuevo.setPrimerApellido(request.getPrimerApellido());
            nuevo.setSegundoApellido(request.getSegundoApellido());
            nuevo.setCorreo(request.getCorreo().toLowerCase());
            nuevo.setTelefono(request.getTelefono());
            nuevo.setFechaNacimiento(request.getFechaNacimiento());

            // Seguridad inicial
            nuevo.setPassword(passwordEncoder.encode(tempPassword));
            nuevo.setRequirePasswordChange(true); // Usar nuevo campo
            nuevo.setProfileCompleted(true);
            nuevo.setAuthProvider(PersonaEmpleado.AuthProvider.LOCAL);
            nuevo.setAccountStatus(PersonaEmpleado.AccountStatus.ACTIVE);

            Roles rol = rolesService.obtenerPorNombre(request.getRol());
            nuevo.setRol(rol);

            empleadoService.guardar(nuevo);

            // 4. Enviar correo
            enviarCorreoBienvenida(nuevo.getCorreo(), nuevo.getPrimerNombre(), tempPassword, request.getRol());

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Empleado creado exitosamente."));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("message", "Error interno: " + e.getMessage()));
        }
    }

    // --- Endpoint 2: Cambiar Contraseña Inicial (Público/Sin Token previo) ---
    // ESTE ES EL MÉTODO QUE TE FALTABA
    @PostMapping("/cambiar-password-inicial")
    public ResponseEntity<?> cambiarPasswordInicial(@RequestBody CambiarPasswordInicialRequest request) {
        try {
            // 1. Buscar usuario
            PersonaEmpleado empleado = empleadoService.obtenerPorCorreo(request.getCorreo());

            // 2. Validar contraseña temporal actual
            if (!passwordEncoder.matches(request.getPasswordActual(), empleado.getPassword())) {
                return ResponseEntity.badRequest().body(Map.of("message", "La contraseña temporal es incorrecta."));
            }

            // 3. Verificar que realmente necesite cambio
            if (!Boolean.TRUE.equals(empleado.getRequirePasswordChange())) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Esta cuenta ya ha sido activada anteriormente."));
            }

            // 4. Actualizar contraseña
            empleado.setPassword(passwordEncoder.encode(request.getNuevaPassword()));
            empleado.setRequirePasswordChange(false); // Marcar como cambiada
            empleado.setAccountStatus(PersonaEmpleado.AccountStatus.ACTIVE);

            empleadoService.guardar(empleado);

            // 5. Generar Token JWT Automáticamente (Login exitoso)
            String accessToken = jwtTokenProvider.generateToken(
                    empleado.getIdPerEmpleado().longValue(),
                    "EMPLEADO",
                    empleado.getRol().getNombreRol(),
                    empleado.getCorreo());

            // Opcional: Generar refresh token si tu provider lo soporta
            String refreshToken = jwtTokenProvider.generateRefreshToken(
                    empleado.getIdPerEmpleado().longValue(),
                    "EMPLEADO");

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Contraseña actualizada. Sesión iniciada.");
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Usuario no encontrado."));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "Error al procesar solicitud."));
        }
    }

    // --- Endpoint para listar ADMINISTRADORES ---
    @GetMapping("/listar-administradores")
    @PreAuthorize("hasRole('ADMINISTRADOR')") // Solo un admin puede ver a otros admins
    public ResponseEntity<List<UsuarioResponse>> listarAdministradores() {
        try {
            // ID Rol Administrador = 3
            List<PersonaEmpleado> admins = empleadoService.obtenerPorRol(3);

            // Convertimos Entidad -> DTO
            List<UsuarioResponse> respuesta = admins.stream()
                    .map(emp -> new UsuarioResponse(
                            emp.getIdPerEmpleado().longValue(),
                            emp.getCedula(),
                            emp.getPrimerNombre() + " " + emp.getPrimerApellido(),
                            emp.getCorreo(),
                            emp.getTelefono(),
                            emp.getRol().getNombreRol(),
                            emp.getPerfilEmpleadoImg()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // --- Endpoint para listar DOCTORES ---
    @GetMapping("/listar-doctores")
    @PreAuthorize("hasRole('ADMINISTRADOR')") // Solo un admin gestiona doctores
    public ResponseEntity<List<UsuarioResponse>> listarDoctores() {
        try {
            // ID Rol Doctor = 2
            List<PersonaEmpleado> doctores = empleadoService.obtenerPorRol(2);

            List<UsuarioResponse> respuesta = doctores.stream()
                    .map(emp -> new UsuarioResponse(
                            emp.getIdPerEmpleado().longValue(),
                            emp.getCedula(),
                            emp.getPrimerNombre() + " " + emp.getPrimerApellido(),
                            emp.getCorreo(),
                            emp.getTelefono(),
                            emp.getRol().getNombreRol(),
                            emp.getPerfilEmpleadoImg()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    // --- Métodos Privados ---

    private String generarPasswordTemporal() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++)
            sb.append(chars.charAt(random.nextInt(chars.length())));
        return sb.toString();
    }

    private void enviarCorreoBienvenida(String email, String nombre, String tempPass, String rol) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Bienvenido a LactaCare - Credenciales de Acceso");
        msg.setText("Hola " + nombre + ",\n\n" +
                "Has sido registrado como " + rol + " en el sistema LactaCare.\n" +
                "Tu usuario es: " + email + "\n" +
                "Tu contraseña temporal es: " + tempPass + "\n\n" +
                "IMPORTANTE: Al iniciar sesión (con Google o Correo) se te pedirá cambiar esta contraseña obligatoriamente.\n\n"
                +
                "Saludos,\nEquipo LactaCare");
        mailSender.send(msg);
    }
}