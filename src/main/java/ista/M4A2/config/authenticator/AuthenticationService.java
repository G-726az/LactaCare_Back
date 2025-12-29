package ista.M4A2.config.authenticator;

import ista.M4A2.config.AuthException;
import ista.M4A2.models.entity.*;
import ista.M4A2.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import ista.M4A2.config.authenticator.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private PersonaPacienteRepository pacienteRepository;

    @Autowired
    private PersonaEmpleadoRepository empleadoRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private CredentialCodeRepository credentialCodeRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;
<<<<<<< HEAD
    
    @Autowired
    private ista.M4A2.models.services.impl.EmailService emailService;
    
=======

    @Autowired
    private ista.M4A2.models.services.impl.EmailService emailService;

>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
    @Value("${google.auth.valid-audiences}")
    private String validAudiences;

    private GoogleIdTokenVerifier verifier;
<<<<<<< HEAD
    
=======

>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
    // ========================================================================
    // INICIALIZACIÓN
    // ========================================================================

    /**
     * Inicializa el verificador de tokens de Google (Singleton pattern)
     */
    private GoogleIdTokenVerifier getVerifier() {
        if (verifier == null) {
            List<String> audiences = Arrays.asList(validAudiences.split(","));
            verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(audiences)
                    .build();
        }
        return verifier;
    }
<<<<<<< HEAD
    
=======

>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
    // ========================================================================
    // AUTENTICACIÓN - LOGIN TRADICIONAL
    // ========================================================================

    /**
     * Login tradicional con correo y contraseña
     */
    @Transactional
    public AuthResponse authenticateTraditional(LoginRequest request) {
        String correo = request.getCorreo().trim().toLowerCase();

        // 1. Buscar en Pacientes
        Optional<PersonaPaciente> pacienteOpt = pacienteRepository.findByCorreo(correo);
        if (pacienteOpt.isPresent()) {
            PersonaPaciente paciente = pacienteOpt.get();
            validarPassword(request.getPassword(), paciente.getPassword());
            return createAuthResponse(paciente);
        }

        // 2. Buscar en Empleados
        Optional<PersonaEmpleado> empleadoOpt = empleadoRepository.findByCorreo(correo);
        if (empleadoOpt.isPresent()) {
            PersonaEmpleado empleado = empleadoOpt.get();
            validarPassword(request.getPassword(), empleado.getPassword());

            // Verificar si requiere cambio de contraseña temporal
            if (Boolean.TRUE.equals(empleado.getRequirePasswordChange())) {
                throw new AuthException("REQUIRE_PASSWORD_CHANGE",
                        "Debe cambiar su contraseña temporal en el primer inicio de sesión.",
                        empleado.getCorreo());
            }

            return createAuthResponse(empleado);
        }

        // 3. Usuario no encontrado
        throw new AuthException("USUARIO_NO_REGISTRADO",
                "Usuario no registrado, regístrese.");
    }

    // ========================================================================
    // AUTENTICACIÓN - LOGIN CON GOOGLE
    // ========================================================================

    /**
     * Autentica con Google OAuth2 con lógica de vinculación inteligente
<<<<<<< HEAD
=======
     * SOLO PARA PACIENTES - Los empleados deben usar /api/auth/google/empleado
>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
     */
    @Transactional
    public Object authenticateWithGoogle(GoogleAuthRequest request) throws Exception {
        // 1. Verificar el token de Google
        GoogleIdToken idToken = getVerifier().verify(request.getIdToken());

        if (idToken == null) {
            throw new AuthException("GOOGLE_TOKEN_INVALIDO", "Token de Google no válido.");
        }

        Payload payload = idToken.getPayload();
        String googleId = payload.getSubject();
        String email = payload.getEmail().toLowerCase();
        String givenName = (String) payload.get("given_name");
        String familyName = (String) payload.get("family_name");
        String pictureUrl = (String) payload.get("picture");
<<<<<<< HEAD
        
=======

        // 1.5. VALIDACIÓN DE SEGURIDAD: Verificar que NO sea un empleado
        Optional<PersonaEmpleado> empleadoCheck = empleadoRepository.findByCorreo(email);
        if (empleadoCheck.isPresent()) {
            PersonaEmpleado empleado = empleadoCheck.get();
            String rolNombre = empleado.getRol() != null ? empleado.getRol().getNombreRol() : "EMPLEADO";
            throw new AuthException("ROL_MISMATCH",
                    "Tu cuenta está registrada como " + rolNombre + ". " +
                            "Por favor, usa la opción de inicio de sesión para " + rolNombre + ".",
                    rolNombre);
        }

>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
        // 2. Buscar usuario por google_id (Ya vinculados previamente)
        Optional<PersonaPaciente> pacienteGoogle = pacienteRepository.findByGoogleId(googleId);
        if (pacienteGoogle.isPresent()) {
            PersonaPaciente paciente = pacienteGoogle.get();
            if (!paciente.getProfileCompleted()) {
                return createProfileIncompleteResponse(googleId, email, givenName, familyName, pictureUrl);
            }
            return createAuthResponse(paciente);
        }
<<<<<<< HEAD
        
=======

>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
        Optional<PersonaEmpleado> empleadoGoogle = empleadoRepository.findByGoogleId(googleId);
        if (empleadoGoogle.isPresent()) {
            PersonaEmpleado empleado = empleadoGoogle.get();
            verificarPasswordTemporalGoogle(empleado);
            return createAuthResponse(empleado);
        }

        // 3. VINCULACIÓN INTELIGENTE - Buscar por correo para vincular cuentas
        // existentes

        // A. Intentar vincular con Paciente existente
        Optional<PersonaPaciente> pacienteEmail = pacienteRepository.findByCorreo(email);
        if (pacienteEmail.isPresent()) {
            PersonaPaciente paciente = pacienteEmail.get();
            paciente.setGoogleId(googleId);
            paciente.setAuthProvider(PersonaPaciente.AuthProvider.GOOGLE);
            if (paciente.getImagenPerfil() == null) {
                paciente.setImagenPerfil(pictureUrl);
            }
            pacienteRepository.save(paciente);
            return createAuthResponse(paciente);
        }
<<<<<<< HEAD
        
=======

>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
        // B. Intentar vincular con Empleado existente
        Optional<PersonaEmpleado> empleadoEmail = empleadoRepository.findByCorreo(email);
        if (empleadoEmail.isPresent()) {
            PersonaEmpleado empleado = empleadoEmail.get();
            empleado.setGoogleId(googleId);
            empleado.setAuthProvider(PersonaEmpleado.AuthProvider.GOOGLE);
            if (empleado.getPerfilEmpleadoImg() == null) {
                empleado.setPerfilEmpleadoImg(pictureUrl);
            }
            PersonaEmpleado saved = empleadoRepository.save(empleado);
            verificarPasswordTemporalGoogle(saved);
            return createAuthResponse(saved);
        }

        // 4. Usuario completamente nuevo - Crear pre-registro como PACIENTE
        // Los empleados NO se auto-registran, deben ser creados por un administrador
        PersonaPaciente nuevoPaciente = new PersonaPaciente();
        nuevoPaciente.setGoogleId(googleId);
        nuevoPaciente.setCorreo(email);
        nuevoPaciente.setPrimerNombre(givenName);
        nuevoPaciente.setPrimerApellido(familyName);
        nuevoPaciente.setImagenPerfil(pictureUrl);
        nuevoPaciente.setAuthProvider(PersonaPaciente.AuthProvider.GOOGLE);
        nuevoPaciente.setProfileCompleted(false);
        nuevoPaciente.setAccountStatus(PersonaPaciente.AccountStatus.INCOMPLETE);

        pacienteRepository.save(nuevoPaciente);

        return createProfileIncompleteResponse(googleId, email, givenName, familyName, pictureUrl);
    }

    /**
     * Autentica EMPLEADOS (Doctores/Administradores) con Google OAuth2
     * SOLO correos autorizados (lista blanca en Persona_Empleado)
     */
    @Transactional
    public AuthResponse authenticateEmpleadoWithGoogle(GoogleAuthRequest request) throws Exception {
        // 1. Verificar el token de Google
        GoogleIdToken idToken = getVerifier().verify(request.getIdToken());

        if (idToken == null) {
            throw new AuthException("GOOGLE_TOKEN_INVALIDO", "Token de Google no válido.");
        }

        Payload payload = idToken.getPayload();
        String googleId = payload.getSubject();
        String email = payload.getEmail().toLowerCase();
        String pictureUrl = (String) payload.get("picture");

        // 2. VALIDACIÓN DE SEGURIDAD: Verificar que NO sea un paciente
        if (pacienteRepository.existsByCorreo(email)) {
            throw new AuthException("ROL_MISMATCH",
                    "Tu cuenta está registrada como PACIENTE. " +
                            "Por favor, usa la opción de inicio de sesión para pacientes.",
                    "PACIENTE");
        }

        // 3. LISTA BLANCA: Buscar en empleados (solo correos autorizados)
        PersonaEmpleado empleado = empleadoRepository.findByCorreo(email)
                .orElseThrow(() -> new AuthException("UNAUTHORIZED_EMAIL",
                        "Correo no autorizado. Contacta al administrador para obtener acceso."));

        // 4. VALIDACIÓN DE ROL ESPECÍFICO (NUEVO)
        // Verificar que el rol del empleado coincida con el rol solicitado
        if (request.getTipoUsuario() != null && !request.getTipoUsuario().isEmpty()) {
            String rolSolicitado = request.getTipoUsuario().toUpperCase();
            String rolReal = empleado.getRol().getNombreRol().toUpperCase();

            // Normalizar "DOCTOR" a "MEDICO"
            if ("DOCTOR".equals(rolSolicitado)) {
                rolSolicitado = "MEDICO";
            }

            if (!rolReal.equals(rolSolicitado)) {
                throw new AuthException("ROL_MISMATCH",
                        "Tu cuenta está registrada como " + rolReal + ". " +
                                "Por favor, usa la opción de inicio de sesión correcta.",
                        rolReal);
            }
        }

        // 5. Vincular Google si es primera vez
        if (empleado.getGoogleId() == null) {
            empleado.setGoogleId(googleId);
            empleado.setAuthProvider(PersonaEmpleado.AuthProvider.GOOGLE);

            // Guardar imagen de Google SOLO si no tiene
            if (empleado.getPerfilEmpleadoImg() == null ||
                    empleado.getPerfilEmpleadoImg().isEmpty()) {
                empleado.setPerfilEmpleadoImg(pictureUrl);
            }

            // IMPORTANTE: Si tenía password temporal, ya no la necesita
            if (Boolean.TRUE.equals(empleado.getRequirePasswordChange())) {
                empleado.setRequirePasswordChange(false);
            }

            empleado = empleadoRepository.save(empleado);
        }

        // 6. Generar tokens JWT y retornar
        return createAuthResponse(empleado);
    }

    // ========================================================================
    // REGISTRO - PACIENTE
    // ========================================================================

    /**
     * Registrar paciente tradicional (formulario web/móvil)
     */
    @Transactional
    public AuthResponse registerPaciente(RegisterPacienteRequest request) {
        // 1. Validación cruzada en AMBAS tablas
        if (pacienteRepository.existsByCorreo(request.getCorreo()) ||
                empleadoRepository.existsByCorreo(request.getCorreo())) {
            throw new AuthException("CORREO_DUPLICADO",
                    "El correo ya está registrado en el sistema.");
        }
<<<<<<< HEAD
        
        if (pacienteRepository.existsByCedula(request.getCedula()) || 
            empleadoRepository.existsByCedula(request.getCedula())) {
            throw new AuthException("CEDULA_DUPLICADA", 
=======

        if (pacienteRepository.existsByCedula(request.getCedula()) ||
                empleadoRepository.existsByCedula(request.getCedula())) {
            throw new AuthException("CEDULA_DUPLICADA",
>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
                    "La cédula ya está registrada en el sistema.");
        }

        // 2. Validar mayoría de edad
        LocalDate fechaNac = LocalDate.parse(request.getFechaNacimiento());
        if (Period.between(fechaNac, LocalDate.now()).getYears() < 18) {
            throw new AuthException("VALIDATION_ERROR",
                    "El paciente debe ser mayor de edad.");
        }

        // 3. Crear paciente
        PersonaPaciente paciente = new PersonaPaciente();
        paciente.setCedula(request.getCedula());
        paciente.setPrimerNombre(request.getPrimerNombre());
        paciente.setSegundoNombre(request.getSegundoNombre());
        paciente.setPrimerApellido(request.getPrimerApellido());
        paciente.setSegundoApellido(request.getSegundoApellido());
        paciente.setCorreo(request.getCorreo().toLowerCase());
        paciente.setPassword(passwordEncoder.encode(request.getPassword()));
        paciente.setTelefono(request.getTelefono());
        paciente.setFechaNacimiento(fechaNac);

        // Procesar discapacidad
        boolean tieneDiscapacidad = request.getDiscapacidad() != null &&
                (request.getDiscapacidad().equalsIgnoreCase("true") ||
                        request.getDiscapacidad().equalsIgnoreCase("si") ||
                        !request.getDiscapacidad().equalsIgnoreCase("ninguna"));
        paciente.setDiscapacidad(tieneDiscapacidad);

        paciente.setAuthProvider(PersonaPaciente.AuthProvider.LOCAL);
        paciente.setProfileCompleted(true);
        paciente.setAccountStatus(PersonaPaciente.AccountStatus.ACTIVE);
<<<<<<< HEAD
        
=======

>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
        paciente = pacienteRepository.save(paciente);
        return createAuthResponse(paciente);
    }

    /**
     * Completar perfil de paciente (post-Google)
     */
    @Transactional
    public AuthResponse completeProfile(CompleteProfileRequest request) {
        // 1. Buscar usuario por Google ID
        PersonaPaciente paciente = pacienteRepository.findByGoogleId(request.getGoogleId())
                .orElseThrow(() -> new AuthException("USUARIO_NO_ENCONTRADO",
                        "Sesión de Google no encontrada."));

        // 2. Validar unicidad de cédula (cruzada)
        boolean cedulaExiste = pacienteRepository.existsByCedula(request.getCedula()) ||
                empleadoRepository.existsByCedula(request.getCedula());

        if (cedulaExiste && !request.getCedula().equals(paciente.getCedula())) {
            throw new AuthException("CEDULA_DUPLICADA",
                    "La cédula ya está registrada por otro usuario.");
        }

        // 3. Validar mayoría de edad
        LocalDate fechaNac = LocalDate.parse(request.getFechaNacimiento());
        if (Period.between(fechaNac, LocalDate.now()).getYears() < 18) {
            throw new AuthException("VALIDATION_ERROR",
                    "El paciente debe ser mayor de edad.");
        }

        // 4. Actualizar datos del paciente
        paciente.setCedula(request.getCedula());
        paciente.setPrimerNombre(request.getPrimerNombre());
        paciente.setSegundoNombre(request.getSegundoNombre());
        paciente.setPrimerApellido(request.getPrimerApellido());
        paciente.setSegundoApellido(request.getSegundoApellido());
        paciente.setTelefono(request.getTelefono());
        paciente.setFechaNacimiento(fechaNac);

        // Procesar discapacidad
        boolean tieneDiscapacidad = request.getDiscapacidad() != null &&
                (request.getDiscapacidad().equalsIgnoreCase("true") ||
                        request.getDiscapacidad().equalsIgnoreCase("si"));
        paciente.setDiscapacidad(tieneDiscapacidad);

        paciente.setProfileCompleted(true);
        paciente.setAccountStatus(PersonaPaciente.AccountStatus.ACTIVE);

        return createAuthResponse(pacienteRepository.save(paciente));
    }

    // ========================================================================
    // REGISTRO - EMPLEADO (Solo por Administrador)
    // ========================================================================

    /**
     * Registrar empleado con código de credencial (DEPRECATED - usar
     * createEmpleado)
     */
    @Transactional
    public AuthResponse registerEmpleado(RegisterEmpleadoRequest request) {
        // Validar código de credencial
        CredentialCode credentialCode = credentialCodeRepository
                .findByCodeAndIsActive(request.getCodigoCredencial(), true)
                .orElseThrow(() -> new RuntimeException("Código de credenciales inválido"));

        // Validación cruzada
        if (empleadoRepository.existsByCorreo(request.getCorreo()) ||
                pacienteRepository.existsByCorreo(request.getCorreo())) {
            throw new AuthException("CORREO_DUPLICADO",
                    "El correo ya está registrado en el sistema.");
        }
<<<<<<< HEAD
        
        if (empleadoRepository.existsByCedula(request.getCedula()) || 
            pacienteRepository.existsByCedula(request.getCedula())) {
            throw new AuthException("CEDULA_DUPLICADA", 
=======

        if (empleadoRepository.existsByCedula(request.getCedula()) ||
                pacienteRepository.existsByCedula(request.getCedula())) {
            throw new AuthException("CEDULA_DUPLICADA",
>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
                    "La cédula ya está registrada en el sistema.");
        }

        PersonaEmpleado empleado = new PersonaEmpleado();
        empleado.setCedula(request.getCedula());
        empleado.setPrimerNombre(request.getPrimerNombre());
        empleado.setSegundoNombre(request.getSegundoNombre());
        empleado.setPrimerApellido(request.getPrimerApellido());
        empleado.setSegundoApellido(request.getSegundoApellido());
        empleado.setCorreo(request.getCorreo().toLowerCase());
        empleado.setPassword(passwordEncoder.encode(request.getPassword()));
        empleado.setTelefono(request.getTelefono());

        if (request.getFechaNacimiento() != null) {
            empleado.setFechaNacimiento(LocalDate.parse(request.getFechaNacimiento()));
        }

        empleado.setRol(credentialCode.getRol());
        empleado.setAuthProvider(request.getGoogleId() != null ? PersonaEmpleado.AuthProvider.GOOGLE
                : PersonaEmpleado.AuthProvider.LOCAL);
        empleado.setGoogleId(request.getGoogleId());
        empleado.setAccountStatus(PersonaEmpleado.AccountStatus.ACTIVE);
        empleado.setProfileCompleted(true);

        empleado = empleadoRepository.save(empleado);
<<<<<<< HEAD
        
=======

>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
        // Incrementar contador de usos del código
        credentialCode.setCurrentUses(credentialCode.getCurrentUses() + 1);
        credentialCodeRepository.save(credentialCode);

        return createAuthResponse(empleado);
    }
<<<<<<< HEAD
    
=======

>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
    /**
     * Crear empleado por administrador con contraseña temporal
     */
    @Transactional
    public CreateEmpleadoResponse createEmpleado(CreateEmpleadoRequest request) {
        // 1. Validar duplicidad cruzada
        if (empleadoRepository.existsByCorreo(request.getCorreo()) ||
                pacienteRepository.existsByCorreo(request.getCorreo())) {
            throw new AuthException("CORREO_DUPLICADO",
                    "El correo ya está registrado en el sistema.");
        }

        if (empleadoRepository.existsByCedula(request.getCedula()) ||
                pacienteRepository.existsByCedula(request.getCedula())) {
            throw new AuthException("CEDULA_DUPLICADA",
                    "La cédula ya está registrada en el sistema.");
        }

        // 2. Validar que el rol existe
        Roles rol = rolesRepository.findById(request.getRolId())
                .orElseThrow(() -> new AuthException("ROL_NO_ENCONTRADO",
                        "El rol especificado no existe."));

        // 3. Generar contraseña temporal
        String temporaryPassword = ista.M4A2.verificaciones.PasswordGenerator.generateTemporaryPassword();

        // 4. Crear empleado
        PersonaEmpleado empleado = new PersonaEmpleado();
        empleado.setCedula(request.getCedula());
        empleado.setPrimerNombre(request.getPrimerNombre());
        empleado.setSegundoNombre(request.getSegundoNombre());
        empleado.setPrimerApellido(request.getPrimerApellido());
        empleado.setSegundoApellido(request.getSegundoApellido());
        empleado.setCorreo(request.getCorreo().toLowerCase());
        empleado.setTelefono(request.getTelefono());
        empleado.setFechaNacimiento(request.getFechaNacimiento());
        empleado.setPassword(passwordEncoder.encode(temporaryPassword));
        empleado.setRol(rol);
        empleado.setAuthProvider(PersonaEmpleado.AuthProvider.LOCAL);
        empleado.setAccountStatus(PersonaEmpleado.AccountStatus.ACTIVE);
        empleado.setProfileCompleted(true);
        empleado.setRequirePasswordChange(true); // Marcar para cambio obligatorio

        PersonaEmpleado saved = empleadoRepository.save(empleado);

        // 5. Enviar correo con credenciales
        String nombreCompleto = request.getPrimerNombre() + " " + request.getPrimerApellido();
        try {
            emailService.sendWelcomeEmployeeEmail(
                    request.getCorreo(),
                    nombreCompleto,
                    request.getCorreo(),
                    temporaryPassword);
        } catch (Exception e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
        }

        // 6. Retornar respuesta
        return new CreateEmpleadoResponse(
                saved.getIdPerEmpleado(),
                saved.getCorreo(),
                nombreCompleto,
                rol.getNombreRol(),
                temporaryPassword,
                "Empleado creado exitosamente. Se ha enviado un correo con las credenciales.");
    }

    // ========================================================================
    // GESTIÓN DE CONTRASEÑAS
    // ========================================================================

    /**
     * Cambiar contraseña temporal de empleado
     */
    @Transactional
    public AuthResponse changeTemporaryPassword(ChangeTemporaryPasswordRequest request) {
        // 1. Buscar empleado
        PersonaEmpleado empleado = empleadoRepository.findByCorreo(request.getCorreo().toLowerCase())
                .orElseThrow(() -> new AuthException("USUARIO_NO_ENCONTRADO",
                        "Empleado no encontrado."));

        // 2. Validar que requiere cambio de contraseña
        if (!Boolean.TRUE.equals(empleado.getRequirePasswordChange())) {
            throw new AuthException("NO_REQUIERE_CAMBIO",
                    "Este empleado no requiere cambio de contraseña.");
        }

        // 3. Validar contraseña temporal
        if (!passwordEncoder.matches(request.getTemporaryPassword(), empleado.getPassword())) {
            throw new AuthException("TEMPORARY_PASSWORD_INVALID",
                    "Contraseña temporal incorrecta.");
        }

        // 4. Validar nueva contraseña
        String passwordValidation = ista.M4A2.verificaciones.PasswordValidator
                .validatePassword(request.getNewPassword());
        if (passwordValidation != null) {
            throw new AuthException("VALIDATION_ERROR", passwordValidation);
        }

        // 5. Actualizar contraseña
        empleado.setPassword(passwordEncoder.encode(request.getNewPassword()));
        empleado.setRequirePasswordChange(false);
        empleadoRepository.save(empleado);

        // 6. Enviar confirmación por correo
        try {
            emailService.sendPasswordChangedConfirmation(
                    empleado.getCorreo(),
                    empleado.getPrimerNombre() + " " + empleado.getPrimerApellido());
        } catch (Exception e) {
            System.err.println("Error al enviar confirmación: " + e.getMessage());
        }

        // 7. Retornar JWT para login automático
        return createAuthResponse(empleado);
    }

    // ========================================================================
    // VERIFICACIÓN DE CUENTA
    // ========================================================================

    /**
     * Verificar estado de cuenta por correo
     */
    public AccountVerificationResponse verifyAccount(String correo) {
        String email = correo.trim().toLowerCase();

        // 1. Buscar en pacientes
        Optional<PersonaPaciente> pacienteOpt = pacienteRepository.findByCorreo(email);
        if (pacienteOpt.isPresent()) {
            PersonaPaciente p = pacienteOpt.get();
            String metodo = determinarMetodoAuth(p.getPassword(), p.getGoogleId());
            return new AccountVerificationResponse(
                    true,
                    metodo,
                    "PACIENTE",
                    false);
        }

        // 2. Buscar en empleados
        Optional<PersonaEmpleado> empleadoOpt = empleadoRepository.findByCorreo(email);
        if (empleadoOpt.isPresent()) {
            PersonaEmpleado e = empleadoOpt.get();
            String metodo = determinarMetodoAuth(e.getPassword(), e.getGoogleId());
            return new AccountVerificationResponse(
                    true,
                    metodo,
                    e.getRol().getNombreRol(),
                    Boolean.TRUE.equals(e.getRequirePasswordChange()));
        }

        // 3. No existe
        return AccountVerificationResponse.notFound();
    }

    // ========================================================================
    // MÉTODOS AUXILIARES
    // ========================================================================

    /**
     * Validar contraseña
     */
    private void validarPassword(String rawPass, String encodedPass) {
        if (encodedPass == null || !passwordEncoder.matches(rawPass, encodedPass)) {
            throw new AuthException("CREDENCIALES_INCORRECTAS",
                    "Usuario o contraseña incorrectos.");
        }
    }

    /**
     * Verificar si empleado tiene contraseña temporal pendiente (login Google)
     */
    private void verificarPasswordTemporalGoogle(PersonaEmpleado empleado) {
        if (Boolean.TRUE.equals(empleado.getRequirePasswordChange())) {
            throw new AuthException("REQUIRE_PASSWORD_CHANGE",
                    "Es su primer inicio de sesión. Por favor establezca una contraseña permanente.",
                    empleado.getCorreo());
        }
    }

    /**
     * Determinar método de autenticación
     */
    private String determinarMetodoAuth(String password, String googleId) {
        boolean tienePassword = password != null && !password.isEmpty();
        boolean tieneGoogle = googleId != null && !googleId.isEmpty();

        if (tienePassword && tieneGoogle) {
            return "AMBOS";
        } else if (tieneGoogle) {
            return "GOOGLE";
        } else if (tienePassword) {
            return "LOCAL";
        }
        return "NINGUNO";
    }

    /**
     * Crear respuesta de autenticación para Paciente
     */
    private AuthResponse createAuthResponse(PersonaPaciente paciente) {
        String accessToken = jwtTokenProvider.generateToken(
                paciente.getId(),
                "PACIENTE",
                "PACIENTE",
                paciente.getCorreo());
        String refreshToken = jwtTokenProvider.generateRefreshToken(
<<<<<<< HEAD
                paciente.getId(), 
                "PACIENTE"
        );
        
=======
                paciente.getId(),
                "PACIENTE");

>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
        UserInfo userInfo = new UserInfo(
                paciente.getId(),
                paciente.getCorreo(),
                paciente.getPrimerNombre() + " " + paciente.getPrimerApellido(),
                "PACIENTE",
                paciente.getAuthProvider().name(),
                paciente.getImagenPerfil(),
<<<<<<< HEAD
                paciente.getProfileCompleted()
        );
        
=======
                paciente.getProfileCompleted());

>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
        return new AuthResponse(
                accessToken,
                refreshToken,
                jwtTokenProvider.getExpirationInSeconds(),
                userInfo);
    }

    /**
     * Crear respuesta de autenticación para Empleado
     */
    private AuthResponse createAuthResponse(PersonaEmpleado empleado) {
        String rolNombre = empleado.getRol().getNombreRol();
        String accessToken = jwtTokenProvider.generateToken(
                empleado.getIdPerEmpleado().longValue(),
                "EMPLEADO",
                rolNombre,
                empleado.getCorreo());
        String refreshToken = jwtTokenProvider.generateRefreshToken(
<<<<<<< HEAD
                empleado.getIdPerEmpleado().longValue(), 
                "EMPLEADO"
        );
        
=======
                empleado.getIdPerEmpleado().longValue(),
                "EMPLEADO");

>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
        UserInfo userInfo = new UserInfo(
                empleado.getIdPerEmpleado().longValue(),
                empleado.getCorreo(),
                empleado.getPrimerNombre() + " " + empleado.getPrimerApellido(),
                rolNombre,
                empleado.getAuthProvider().name(),
                empleado.getPerfilEmpleadoImg(),
<<<<<<< HEAD
                empleado.getProfileCompleted()
        );
        
=======
                empleado.getProfileCompleted());

>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
        return new AuthResponse(
                accessToken,
                refreshToken,
                jwtTokenProvider.getExpirationInSeconds(),
                userInfo);
    }

    /**
     * Crear respuesta de perfil incompleto
     */
    private ProfileIncompleteResponse createProfileIncompleteResponse(
            String googleId, String email, String givenName,
            String familyName, String pictureUrl) {

        GoogleUserData googleData = new GoogleUserData(
                googleId,
                email,
                givenName + " " + familyName,
                givenName,
                familyName,
                pictureUrl);

        List<String> requiredFields = Arrays.asList(
                "cedula",
                "fechaNacimiento",
                "telefono");

        return new ProfileIncompleteResponse(
<<<<<<< HEAD
                "Por favor completa tu perfil para continuar", 
                googleData, 
                requiredFields
        );
=======
                "Por favor completa tu perfil para continuar",
                googleData,
                requiredFields);
>>>>>>> fd5e5d2 (Cambios en SecurityConfig, ubicacion de dto's, y ajustes en entity y controllers para manejo de sala_lactancia e intituciones)
    }
}