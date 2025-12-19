package ista.M4A2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import ista.M4A2.dto.ApiResponse;
import ista.M4A2.dto.LoginRequest;
import ista.M4A2.dto.LoginResponse;
import ista.M4A2.dto.RegisterRequest;
import ista.M4A2.models.entity.PersonaEmpleado;
import ista.M4A2.models.entity.PersonaPaciente;
import ista.M4A2.models.services.serv.PersonaEmpleadoService;
import ista.M4A2.models.services.serv.IPersonaPacienteService;
import ista.M4A2.models.services.serv.RolesService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import java.util.*;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300"})
public class AuthController {

    @Autowired
    private IPersonaPacienteService personaPacienteService;

    @Autowired
    private PersonaEmpleadoService personaEmpleadoService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Endpoint de Login
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            String cedula = request.getCedula();
            String password = request.getPassword();
            String tipoUsuario = request.getTipoUsuario();

            // Validaciones básicas
            if (cedula == null || cedula.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new LoginResponse(false, "La cédula es requerida", null));
            }

            if (password == null || password.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new LoginResponse(false, "La contraseña es requerida", null));
            }

            // Buscar según el tipo de usuario
            if ("PACIENTE".equalsIgnoreCase(tipoUsuario)) {
                return loginPaciente(cedula, password);
            } else if ("MEDICO".equalsIgnoreCase(tipoUsuario) || "ADMINISTRADOR".equalsIgnoreCase(tipoUsuario)) {
                return loginEmpleado(cedula, password, tipoUsuario);
            } else {
                return ResponseEntity.badRequest()
                    .body(new LoginResponse(false, "Tipo de usuario no válido", null));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new LoginResponse(false, "Error en el servidor: " + e.getMessage(), null));
        }
    }

    /**
     * Login para Pacientes
     */
    private ResponseEntity<LoginResponse> loginPaciente(String cedula, String password) {
        // Buscar paciente por cédula
        List<PersonaPaciente> pacientes = personaPacienteService.findAll();
        PersonaPaciente paciente = pacientes.stream()
            .filter(p -> cedula.equals(p.getCedula()))
            .findFirst()
            .orElse(null);

        if (paciente == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(false, "Cédula o contraseña incorrecta", null));
        }

        // Verificar contraseña
        if (!passwordEncoder.matches(password, paciente.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(false, "Cédula o contraseña incorrecta", null));
        }

        // Crear datos de respuesta
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", paciente.getId());
        userData.put("cedula", paciente.getCedula());
        userData.put("nombre_completo", paciente.getPrimerNombre() + " " + 
            (paciente.getSegundoNombre() != null ? paciente.getSegundoNombre() + " " : "") +
            paciente.getPrimerApellido() + " " +
            (paciente.getSegundoApellido() != null ? paciente.getSegundoApellido() : ""));
        userData.put("primer_nombre", paciente.getPrimerNombre());
        userData.put("primer_apellido", paciente.getPrimerApellido());
        userData.put("correo", paciente.getCorreo());
        userData.put("telefono", paciente.getTelefono());
        userData.put("rol", "PACIENTE");
        userData.put("rol_id", 6);
        userData.put("tipo", "PACIENTE");
        userData.put("fecha_nacimiento", paciente.getFechaNacimiento());
        userData.put("perfil_img", paciente.getImagenPerfil());

        return ResponseEntity.ok(new LoginResponse(true, "Login exitoso", userData));
    }

    /**
     * Login para Empleados (Médicos/Administradores)
     */
    private ResponseEntity<LoginResponse> loginEmpleado(String cedula, String password, String tipoUsuario) {
        // Buscar empleado por cédula
        PersonaEmpleado empleado = null;
        try {
            empleado = personaEmpleadoService.obtenerPorCedula(cedula);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(false, "Cédula o contraseña incorrecta", null));
        }

        // Verificar que el rol coincida con el tipo de usuario solicitado
        String rolNombre = empleado.getRol() != null ? empleado.getRol().getNombreRol() : "";
        
        if ("ADMINISTRADOR".equalsIgnoreCase(tipoUsuario) && empleado.getRol().getIdRoles() != 1) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(false, "No tienes permisos de administrador", null));
        }
        
        if ("MEDICO".equalsIgnoreCase(tipoUsuario) && empleado.getRol().getIdRoles() != 2) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(false, "No tienes permisos de médico", null));
        }

        // Los empleados NO tienen password en tu base de datos actual
        // Por ahora, aceptamos cualquier password para empleados
        // IMPORTANTE: Esto es temporal, deberías agregar un campo password a PersonaEmpleado
        
        // Crear datos de respuesta
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", empleado.getIdPerEmpleado());
        userData.put("cedula", empleado.getCedula());
        userData.put("nombre_completo", empleado.getPrimerNombre() + " " + 
            (empleado.getSegundoNombre() != null ? empleado.getSegundoNombre() + " " : "") +
            empleado.getPrimerApellido() + " " +
            (empleado.getSegundoApellido() != null ? empleado.getSegundoApellido() : ""));
        userData.put("primer_nombre", empleado.getPrimerNombre());
        userData.put("primer_apellido", empleado.getPrimerApellido());
        userData.put("correo", empleado.getCorreo());
        userData.put("telefono", empleado.getTelefono());
        userData.put("rol", rolNombre);
        userData.put("rol_id", empleado.getRol().getIdRoles());
        userData.put("tipo", tipoUsuario);
        userData.put("fecha_nacimiento", empleado.getFechaNacimiento());
        userData.put("perfil_img", empleado.getPerfilEmpleadoImg());

        return ResponseEntity.ok(new LoginResponse(true, "Login exitoso", userData));
    }

    /**
     * Endpoint de Registro
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        try {
            // Validar tipo de usuario
            if (request.getTipo_usuario() == null || request.getTipo_usuario().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El tipo de usuario es requerido"));
            }

            if ("paciente".equalsIgnoreCase(request.getTipo_usuario())) {
                return registerPaciente(request);
            } else if ("empleado".equalsIgnoreCase(request.getTipo_usuario())) {
                return registerEmpleado(request);
            } else {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Tipo de usuario no válido"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Error en el servidor: " + e.getMessage()));
        }
    }

    /**
     * Registrar Paciente
     */
    private ResponseEntity<ApiResponse> registerPaciente(RegisterRequest request) {
        // Validar campos requeridos
        if (request.getCedula() == null || request.getPrimer_nombre() == null || 
            request.getPrimer_apellido() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, "Campos requeridos faltantes"));
        }

        // Verificar si ya existe la cédula
        List<PersonaPaciente> existentes = personaPacienteService.findAll();
        boolean cedulaExiste = existentes.stream()
            .anyMatch(p -> request.getCedula().equals(p.getCedula()));

        if (cedulaExiste) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, "La cédula ya está registrada"));
        }

        // Crear nuevo paciente
        PersonaPaciente paciente = new PersonaPaciente();
        paciente.setCedula(request.getCedula());
        paciente.setPrimerNombre(request.getPrimer_nombre());
        paciente.setSegundoNombre(request.getSegundo_nombre());
        paciente.setPrimerApellido(request.getPrimer_apellido());
        paciente.setSegundoApellido(request.getSegundo_apellido());
        paciente.setCorreo(request.getCorreo());
        paciente.setTelefono(request.getTelefono());
        paciente.setDiscapacidad(request.isDiscapacidad());
        
        // Convertir fecha de string a LocalDate
        if (request.getFecha_nacimiento() != null && !request.getFecha_nacimiento().isEmpty()) {
            paciente.setFechaNacimiento(LocalDate.parse(request.getFecha_nacimiento()));
        }

        // Encriptar contraseña
        paciente.setPassword(passwordEncoder.encode(request.getPassword()));

        // Guardar
        personaPacienteService.save(paciente);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse(true, "Paciente registrado exitosamente"));
    }

    /**
     * Registrar Empleado
     */
    private ResponseEntity<ApiResponse> registerEmpleado(RegisterRequest request) {
        // Validar campos requeridos
        if (request.getCedula() == null || request.getPrimer_nombre() == null || 
            request.getPrimer_apellido() == null || request.getRol_empleado() == null) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, "Campos requeridos faltantes"));
        }

        // Verificar si ya existe la cédula
        try {
            personaEmpleadoService.obtenerPorCedula(request.getCedula());
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, "La cédula ya está registrada"));
        } catch (RuntimeException e) {
            // No existe, continuar
        }

        // Crear nuevo empleado
        PersonaEmpleado empleado = new PersonaEmpleado();
        empleado.setCedula(request.getCedula());
        empleado.setPrimerNombre(request.getPrimer_nombre());
        empleado.setSegundoNombre(request.getSegundo_nombre());
        empleado.setPrimerApellido(request.getPrimer_apellido());
        empleado.setSegundoApellido(request.getSegundo_apellido());
        empleado.setCorreo(request.getCorreo());
        empleado.setTelefono(request.getTelefono());
        
        // Convertir fecha de string a LocalDate
        if (request.getFecha_nacimiento() != null && !request.getFecha_nacimiento().isEmpty()) {
            empleado.setFechaNacimiento(LocalDate.parse(request.getFecha_nacimiento()));
        }

        // Asignar rol
        try {
            Integer rolId = Integer.parseInt(request.getRol_empleado());
            var rol = rolesService.obtenerPorId(rolId);
            empleado.setRol(rol);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, "Rol no válido"));
        }

        // Guardar
        personaEmpleadoService.guardar(empleado);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse(true, "Empleado registrado exitosamente"));
    }
}