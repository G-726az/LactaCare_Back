package ista.M4A2.config.authenticator;

import jakarta.validation.Valid;
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
import ista.M4A2.verificaciones.PasswordValidator;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;
    
    @Autowired
    private IPersonaPacienteService personaPacienteService;

    @Autowired
    private PersonaEmpleadoService personaEmpleadoService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    // =====================================================
    // ENDPOINTS DE GOOGLE OAUTH2 Y JWT (Sistema Nuevo)
    // =====================================================
    
    /**
     * Login con Google OAuth2
     * POST /api/auth/google
     */
    @PostMapping("/google")
    public ResponseEntity<?> authenticateWithGoogle(@Valid @RequestBody GoogleAuthRequest request) {
        try {
            Object response = authenticationService.authenticateWithGoogle(request);
            
            if (response instanceof AuthResponse) {
                return ResponseEntity.ok(response);
            } else if (response instanceof ProfileIncompleteResponse) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
            }
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error inesperado", "ERROR"));
                    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("AUTHENTICATION_FAILED", e.getMessage(), "/api/auth/google", 401));
        }
    }
    
    /**
     * Registro de nuevo paciente con JWT
     * POST /api/auth/register/paciente
     */
    @PostMapping("/register/paciente")
    public ResponseEntity<?> registerPaciente(@Valid @RequestBody RegisterPacienteRequest request) {
        try {
            AuthResponse response = authenticationService.registerPaciente(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("REGISTRATION_FAILED", e.getMessage(), "/api/auth/register/paciente", 400));
        }
    }
    
    /**
     * Registro de nuevo empleado (Doctor/Admin) con JWT
     * POST /api/auth/register/empleado
     */
    @PostMapping("/register/empleado")
    public ResponseEntity<?> registerEmpleado(@Valid @RequestBody RegisterEmpleadoRequest request) {
        try {
            AuthResponse response = authenticationService.registerEmpleado(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            HttpStatus status = e.getMessage().contains("Código de credenciales") ? 
                    HttpStatus.FORBIDDEN : HttpStatus.BAD_REQUEST;
            
            return ResponseEntity.status(status)
                    .body(new ErrorResponse("REGISTRATION_FAILED", e.getMessage(), "/api/auth/register/empleado", status.value()));
        }
    }
    
    /**
     * Completar perfil después de Google OAuth (primera vez)
     * POST /api/auth/complete-profile
     */
    @PostMapping("/complete-profile")
    public ResponseEntity<?> completeProfile(@Valid @RequestBody CompleteProfileRequest request) {
        try {
            AuthResponse response = authenticationService.completeProfile(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("PROFILE_COMPLETION_FAILED", e.getMessage(), "/api/auth/complete-profile", 400));
        }
    }
    
    // =====================================================
    // ENDPOINTS DE LOGIN TRADICIONAL (Sistema Legacy Integrado)
    // =====================================================
    
    /**
     * Login tradicional con cédula y password (UNIFICADO)
     * Soporta tanto el formato nuevo (JWT) como el formato legacy
     * POST /api/auth/login
     */

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
        String correo = request.getCorreo().trim().toLowerCase();
        String password = request.getPassword();
        String tipoUsuario = request.getTipoUsuario();

        // Validaciones básicas
        if (correo == null || correo.isEmpty()) {
            return ResponseEntity.badRequest()
                .body(new LoginResponse(false, "El correo es requerido", null));
        }

        if (password == null || password.isEmpty()) {
            return ResponseEntity.badRequest()
                .body(new LoginResponse(false, "La contraseña es requerida", null));
        }

        // Buscar según el tipo de usuario
        if ("PACIENTE".equalsIgnoreCase(tipoUsuario)) {
            return loginPacienteUnificado(correo, password);
        } else if ("MEDICO".equalsIgnoreCase(tipoUsuario) || "ADMINISTRADOR".equalsIgnoreCase(tipoUsuario)) {
            return loginEmpleadoUnificado(correo, password, tipoUsuario);
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
 * Login unificado para Pacientes (con JWT)
 */
private ResponseEntity<LoginResponse> loginPacienteUnificado(String correo, String password) {
    // Buscar paciente por correo
    List<PersonaPaciente> pacientes = personaPacienteService.findAll();
    PersonaPaciente paciente = pacientes.stream()
        .filter(p -> correo.equalsIgnoreCase(p.getCorreo()))
        .findFirst()
        .orElse(null);

    if (paciente == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new LoginResponse(false, "Correo o contraseña incorrecta", null));
    }

    // Verificar contraseña
    if (!passwordEncoder.matches(password, paciente.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new LoginResponse(false, "Correo o contraseña incorrecta", null));
    }

    // Generar JWT Token
    String accessToken = jwtTokenProvider.generateToken(
        paciente.getId(), 
        "PACIENTE", 
        "PACIENTE", 
        paciente.getCorreo()
    );
    
    String refreshToken = jwtTokenProvider.generateRefreshToken(
        paciente.getId(), 
        "PACIENTE"
    );

    // Crear datos de respuesta con tokens
    Map<String, Object> userData = new HashMap<>();
    userData.put("id", paciente.getId());
    userData.put("cedula", paciente.getCedula());
    userData.put("correo", paciente.getCorreo());
    userData.put("nombre_completo", paciente.getPrimerNombre() + " " + 
        (paciente.getSegundoNombre() != null ? paciente.getSegundoNombre() + " " : "") +
        paciente.getPrimerApellido() + " " +
        (paciente.getSegundoApellido() != null ? paciente.getSegundoApellido() : ""));
    userData.put("primer_nombre", paciente.getPrimerNombre());
    userData.put("primer_apellido", paciente.getPrimerApellido());
    userData.put("telefono", paciente.getTelefono());
    userData.put("rol", "PACIENTE");
    userData.put("rol_id", 6);
    userData.put("tipo", "PACIENTE");
    userData.put("fecha_nacimiento", paciente.getFechaNacimiento());
    userData.put("perfil_img", paciente.getImagenPerfil());
    userData.put("access_token", accessToken);
    userData.put("refresh_token", refreshToken);
    userData.put("token_type", "Bearer");
    userData.put("expires_in", jwtTokenProvider.getExpirationInSeconds());

    return ResponseEntity.ok(new LoginResponse(true, "Login exitoso", userData));
}

/**
 * Login unificado para Empleados (Médicos/Administradores) con JWT
 */
private ResponseEntity<LoginResponse> loginEmpleadoUnificado(String correo, String password, String tipoUsuario) {
    System.out.println("\n========== DEBUG LOGIN EMPLEADO ==========");
    System.out.println("1. Correo recibido: [" + correo + "]");
    System.out.println("2. Password recibida: [" + password + "]");
    System.out.println("3. Tipo usuario recibido: [" + tipoUsuario + "]");
    
    // Buscar empleado por correo
    PersonaEmpleado empleado = null;
    try {
        empleado = personaEmpleadoService.obtenerPorCorreo(correo);
        System.out.println("4. ✅ Empleado encontrado:");
        System.out.println("   - ID: " + empleado.getIdPerEmpleado());
        System.out.println("   - Nombre: " + empleado.getPrimerNombre() + " " + empleado.getPrimerApellido());
        System.out.println("   - Correo en BD: [" + empleado.getCorreo() + "]");
        System.out.println("   - Rol ID: " + (empleado.getRol() != null ? empleado.getRol().getIdRoles() : "NULL"));
        System.out.println("   - Rol Nombre: " + (empleado.getRol() != null ? empleado.getRol().getNombreRol() : "NULL"));
        
    } catch (RuntimeException e) {
        System.out.println("4. ❌ ERROR: No se encontró empleado con correo: " + correo);
        System.out.println("   Excepción: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new LoginResponse(false, "Correo o contraseña incorrecta", null));
    }

    // Verificar que el rol coincida con el tipo de usuario solicitado
    System.out.println("5. Verificando permisos...");
    String rolNombre = empleado.getRol() != null ? empleado.getRol().getNombreRol() : "";
    Integer rolId = empleado.getRol() != null ? empleado.getRol().getIdRoles() : null;
    
    System.out.println("   - Rol en BD: " + rolNombre + " (ID: " + rolId + ")");
    System.out.println("   - Tipo usuario solicitado: " + tipoUsuario);
    
    if ("ADMINISTRADOR".equalsIgnoreCase(tipoUsuario)) {
        System.out.println("   - Validando rol de ADMINISTRADOR...");
        if (rolId == null || (rolId != 1 && rolId != 3)) { // Permitir ID 1 o 3
            System.out.println("   ❌ RECHAZADO: Se esperaba rol_id=1 o 3, pero tiene rol_id=" + rolId);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(false, "No tienes permisos de administrador", null));
        }
        System.out.println("   ✅ Rol de administrador verificado");
    }
    
    if ("MEDICO".equalsIgnoreCase(tipoUsuario)) {
        System.out.println("   - Validando rol de MÉDICO...");
        if (rolId == null || rolId != 2) {
            System.out.println("   ❌ RECHAZADO: Se esperaba rol_id=2, pero tiene rol_id=" + rolId);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(false, "No tienes permisos de médico", null));
        }
        System.out.println("   ✅ Rol de médico verificado");
    }

    // Verificar contraseña
    System.out.println("6. Verificando contraseña...");
    
    if (empleado.getPassword() == null || empleado.getPassword().isEmpty()) {
        System.out.println("   ❌ ERROR: Usuario sin contraseña en BD");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new LoginResponse(false, "Usuario sin contraseña configurada. Contacte al administrador", null));
    }
    
    boolean passwordMatch = passwordEncoder.matches(password, empleado.getPassword());
    System.out.println("   - passwordEncoder.matches() resultado: " + passwordMatch);
    
    if (!passwordMatch) {
        System.out.println("   ❌ CONTRASEÑA INCORRECTA");
        System.out.println("==========================================\n");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new LoginResponse(false, "Correo o contraseña incorrecta", null));
    }
    
    System.out.println("   ✅ CONTRASEÑA CORRECTA");
    System.out.println("7. ✅ LOGIN EXITOSO - Generando tokens...");
    
    // Generar JWT Token
    String accessToken = jwtTokenProvider.generateToken(
        empleado.getIdPerEmpleado().longValue(), 
        "EMPLEADO", 
        rolNombre, 
        empleado.getCorreo()
    );
    
    String refreshToken = jwtTokenProvider.generateRefreshToken(
        empleado.getIdPerEmpleado().longValue(), 
        "EMPLEADO"
    );
    
    // Crear datos de respuesta con tokens
    Map<String, Object> userData = new HashMap<>();
    userData.put("id", empleado.getIdPerEmpleado());
    userData.put("cedula", empleado.getCedula());
    userData.put("correo", empleado.getCorreo());
    userData.put("nombre_completo", empleado.getPrimerNombre() + " " + 
        (empleado.getSegundoNombre() != null ? empleado.getSegundoNombre() + " " : "") +
        empleado.getPrimerApellido() + " " +
        (empleado.getSegundoApellido() != null ? empleado.getSegundoApellido() : ""));
    userData.put("primer_nombre", empleado.getPrimerNombre());
    userData.put("primer_apellido", empleado.getPrimerApellido());
    userData.put("telefono", empleado.getTelefono());
    userData.put("rol", rolNombre);
    userData.put("rol_id", empleado.getRol().getIdRoles());
    userData.put("tipo", tipoUsuario);
    userData.put("fecha_nacimiento", empleado.getFechaNacimiento());
    userData.put("perfil_img", empleado.getPerfilEmpleadoImg());
    userData.put("access_token", accessToken);
    userData.put("refresh_token", refreshToken);
    userData.put("token_type", "Bearer");
    userData.put("expires_in", jwtTokenProvider.getExpirationInSeconds());

    System.out.println("8. ✅ Respuesta preparada exitosamente");
    System.out.println("==========================================\n");
    
    return ResponseEntity.ok(new LoginResponse(true, "Login exitoso", userData));
}
    
    // =====================================================
    // ENDPOINTS DE REGISTRO LEGACY (Integrados)
    // =====================================================
    
    /**
     * Endpoint de Registro Legacy (mantiene compatibilidad)
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerLegacy(@RequestBody RegisterRequest request) {
        try {
            // Validar tipo de usuario
            if (request.getTipo_usuario() == null || request.getTipo_usuario().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "El tipo de usuario es requerido"));
            }

            if ("paciente".equalsIgnoreCase(request.getTipo_usuario())) {
                return registerPacienteLegacy(request);
            } else if ("empleado".equalsIgnoreCase(request.getTipo_usuario())) {
                return registerEmpleadoLegacy(request);
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
     * Registrar Paciente (formato legacy)
     */
    private ResponseEntity<ApiResponse> registerPacienteLegacy(RegisterRequest request) {
        // Validar campos requeridos
        if (request.getCedula() == null || request.getPrimer_nombre() == null || 
            request.getPrimer_apellido() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, "Campos requeridos faltantes"));
        }

        // Validar contraseña
        String passwordError = PasswordValidator.validatePassword(request.getPassword());
        if (passwordError != null) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, passwordError));
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
        
        // Configurar valores por defecto para autenticación
        paciente.setAuthProvider(PersonaPaciente.AuthProvider.LOCAL);
        paciente.setAccountStatus(PersonaPaciente.AccountStatus.ACTIVE);
        paciente.setProfileCompleted(true);

        // Guardar
        personaPacienteService.save(paciente);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse(true, "Paciente registrado exitosamente"));
    }

    /**
     * Registrar Empleado (formato legacy)
     */
    private ResponseEntity<ApiResponse> registerEmpleadoLegacy(RegisterRequest request) {
        // Validar campos requeridos
        if (request.getCedula() == null || request.getPrimer_nombre() == null || 
            request.getPrimer_apellido() == null || request.getRol_empleado() == null ||
            request.getPassword() == null) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, "Campos requeridos faltantes (incluye contraseña)"));
        }

        // Validar contraseña
        String passwordError = PasswordValidator.validatePassword(request.getPassword());
        if (passwordError != null) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, passwordError));
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

        // Encriptar contraseña
        empleado.setPassword(passwordEncoder.encode(request.getPassword()));

        // Asignar rol
        try {
            Integer rolId = Integer.parseInt(request.getRol_empleado());
            var rol = rolesService.obtenerPorId(rolId);
            empleado.setRol(rol);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, "Rol no válido"));
        }
        
        // Configurar valores por defecto para autenticación
        empleado.setAuthProvider(PersonaEmpleado.AuthProvider.LOCAL);
        empleado.setAccountStatus(PersonaEmpleado.AccountStatus.ACTIVE);
        empleado.setProfileCompleted(true);

        // Guardar
        personaEmpleadoService.guardar(empleado);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse(true, "Empleado registrado exitosamente"));
    }
    
    // =====================================================
    // ENDPOINT DE HEALTH CHECK
    // =====================================================
    
    /**
     * Health check del servicio de autenticación
     * GET /api/auth/health
     */
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(new MessageResponse("Authentication service is running", "SUCCESS"));
    }
    
    @GetMapping("/generate-hash/{password}")
    public ResponseEntity<?> generateHash(@PathVariable String password) {
        String hash = passwordEncoder.encode(password);
        
        Map<String, Object> response = new HashMap<>();
        response.put("password_plain", password);
        response.put("password_hash", hash);
        response.put("mensaje", "Copia este hash y úsalo en tu UPDATE SQL");
        
        return ResponseEntity.ok(response);
    }
    
}