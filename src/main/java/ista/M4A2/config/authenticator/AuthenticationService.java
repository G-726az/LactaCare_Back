package ista.M4A2.config.authenticator;
<<<<<<< HEAD
<<<<<<< HEAD
import ista.M4A2.models.entity.*;
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)

import ista.M4A2.config.AuthException;
import ista.M4A2.models.entity.*;
import ista.M4A2.dto.*;
<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
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

import java.time.LocalDate;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import java.time.Period;
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
import java.time.Period;
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

<<<<<<< HEAD
<<<<<<< HEAD
	@Autowired
=======
    @Autowired
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
    @Autowired
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
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
<<<<<<< HEAD
=======
    @Autowired
    private ista.M4A2.models.services.impl.EmailService emailService;
    
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
    @Autowired
    private ista.M4A2.models.services.impl.EmailService emailService;
    
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
    @Value("${google.auth.valid-audiences}")
    private String validAudiences;
    
    private GoogleIdTokenVerifier verifier;
    
<<<<<<< HEAD
<<<<<<< HEAD
    /**
     * Inicializa el verificador de tokens de Google
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
    // ========================================================================
    // INICIALIZACIÓN
    // ========================================================================
    
    /**
     * Inicializa el verificador de tokens de Google (Singleton pattern)
<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
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
<<<<<<< HEAD
    /**
     * Autentica con Google OAuth2
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
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
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
     */
    @Transactional
    public Object authenticateWithGoogle(GoogleAuthRequest request) throws Exception {
        // 1. Verificar el token de Google
        GoogleIdToken idToken = getVerifier().verify(request.getIdToken());
        
        if (idToken == null) {
<<<<<<< HEAD
<<<<<<< HEAD
            throw new RuntimeException("Token de Google inválido");
=======
            throw new AuthException("GOOGLE_TOKEN_INVALIDO", "Token de Google no válido.");
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
            throw new AuthException("GOOGLE_TOKEN_INVALIDO", "Token de Google no válido.");
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        }
        
        Payload payload = idToken.getPayload();
        String googleId = payload.getSubject();
<<<<<<< HEAD
<<<<<<< HEAD
        String email = payload.getEmail();
        String name = (String) payload.get("name");
=======
        String email = payload.getEmail().toLowerCase();
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
        String email = payload.getEmail().toLowerCase();
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        String givenName = (String) payload.get("given_name");
        String familyName = (String) payload.get("family_name");
        String pictureUrl = (String) payload.get("picture");
        
<<<<<<< HEAD
<<<<<<< HEAD
        // 2. Buscar usuario por google_id
        Optional<PersonaPaciente> pacienteOpt = pacienteRepository.findByGoogleId(googleId);
        Optional<PersonaEmpleado> empleadoOpt = empleadoRepository.findByGoogleId(googleId);
        
        // 3. Si existe, generar JWT
        if (pacienteOpt.isPresent()) {
            PersonaPaciente paciente = pacienteOpt.get();
            if (!paciente.getProfileCompleted()) {
                return createProfileIncompleteResponse(googleId, email, name, givenName, familyName, pictureUrl);
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        // 2. Buscar usuario por google_id (Ya vinculados previamente)
        Optional<PersonaPaciente> pacienteGoogle = pacienteRepository.findByGoogleId(googleId);
        if (pacienteGoogle.isPresent()) {
            PersonaPaciente paciente = pacienteGoogle.get();
            if (!paciente.getProfileCompleted()) {
                return createProfileIncompleteResponse(googleId, email, givenName, familyName, pictureUrl);
<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
            }
            return createAuthResponse(paciente);
        }
        
<<<<<<< HEAD
<<<<<<< HEAD
        if (empleadoOpt.isPresent()) {
            PersonaEmpleado empleado = empleadoOpt.get();
            if (!empleado.getProfileCompleted()) {
                return createProfileIncompleteResponse(googleId, email, name, givenName, familyName, pictureUrl);
            }
            return createAuthResponse(empleado);
        }
        
        // 4. Usuario nuevo - buscar por email para vincular
        Optional<PersonaPaciente> pacientePorEmail = pacienteRepository.findByCorreo(email);
        if (pacientePorEmail.isPresent()) {
            PersonaPaciente paciente = pacientePorEmail.get();
            paciente.setGoogleId(googleId);
            paciente.setAuthProvider(PersonaPaciente.AuthProvider.GOOGLE);
            if (pictureUrl != null) {
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        Optional<PersonaEmpleado> empleadoGoogle = empleadoRepository.findByGoogleId(googleId);
        if (empleadoGoogle.isPresent()) {
            PersonaEmpleado empleado = empleadoGoogle.get();
            verificarPasswordTemporalGoogle(empleado);
            return createAuthResponse(empleado);
        }
        
        // 3. VINCULACIÓN INTELIGENTE - Buscar por correo para vincular cuentas existentes
        
        // A. Intentar vincular con Paciente existente
        Optional<PersonaPaciente> pacienteEmail = pacienteRepository.findByCorreo(email);
        if (pacienteEmail.isPresent()) {
            PersonaPaciente paciente = pacienteEmail.get();
            paciente.setGoogleId(googleId);
            paciente.setAuthProvider(PersonaPaciente.AuthProvider.GOOGLE);
            if (paciente.getImagenPerfil() == null) {
<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
                paciente.setImagenPerfil(pictureUrl);
            }
            pacienteRepository.save(paciente);
            return createAuthResponse(paciente);
        }
        
<<<<<<< HEAD
<<<<<<< HEAD
        Optional<PersonaEmpleado> empleadoPorEmail = empleadoRepository.findByCorreo(email);
        if (empleadoPorEmail.isPresent()) {
            PersonaEmpleado empleado = empleadoPorEmail.get();
            empleado.setGoogleId(googleId);
            empleado.setAuthProvider(PersonaEmpleado.AuthProvider.GOOGLE);
            if (pictureUrl != null) {
                empleado.setPerfilEmpleadoImg(pictureUrl);
            }
            empleadoRepository.save(empleado);
            return createAuthResponse(empleado);
        }
        
     // 5. Usuario completamente nuevo - CREAR REGISTRO INCOMPLETO
     // Decidimos por defecto que sea PACIENTE, si fuera empleado usaría un código luego
     PersonaPaciente nuevoPaciente = new PersonaPaciente();
     nuevoPaciente.setGoogleId(googleId);
     nuevoPaciente.setCorreo(email); // Guardamos el correo de Google
     nuevoPaciente.setPrimerNombre(givenName);
     nuevoPaciente.setPrimerApellido(familyName);
     nuevoPaciente.setImagenPerfil(pictureUrl);
     nuevoPaciente.setAuthProvider(PersonaPaciente.AuthProvider.GOOGLE);
     nuevoPaciente.setProfileCompleted(false); // Importante: Incompleto
     nuevoPaciente.setAccountStatus(PersonaPaciente.AccountStatus.ACTIVE);

     pacienteRepository.save(nuevoPaciente); // <--- GUARDAMOS EN BD

     return createProfileIncompleteResponse(googleId, email, name, givenName, familyName, pictureUrl);
    }
    
    /**
     * Login tradicional
     */
    public AuthResponse authenticateTraditional(LoginRequest request) {
        // Buscar en pacientes
        Optional<PersonaPaciente> pacienteOpt = pacienteRepository.findByCorreo(request.getCorreo());
        if (pacienteOpt.isPresent()) {
            PersonaPaciente paciente = pacienteOpt.get();
            if (passwordEncoder.matches(request.getPassword(), paciente.getPassword())) {
                return createAuthResponse(paciente);
            }
        }
        
        // Buscar en empleados
        Optional<PersonaEmpleado> empleadoOpt = empleadoRepository.findByCorreo(request.getCorreo());
        if (empleadoOpt.isPresent()) {
            PersonaEmpleado empleado = empleadoOpt.get();
            if (passwordEncoder.matches(request.getPassword(), empleado.getPassword())) {
                return createAuthResponse(empleado);
            }
        }
        
        throw new RuntimeException("Credenciales incorrectas");
    }
    
    /**
     * Registrar paciente
     */
    @Transactional
    public AuthResponse registerPaciente(RegisterPacienteRequest request) {
        // VALIDACIÓN CRUZADA: Buscar en ambas tablas
        if (pacienteRepository.existsByCorreo(request.getCorreo()) || 
            empleadoRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado en el sistema");
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
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
<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        }
        
        if (pacienteRepository.existsByCedula(request.getCedula()) || 
            empleadoRepository.existsByCedula(request.getCedula())) {
<<<<<<< HEAD
<<<<<<< HEAD
            throw new RuntimeException("La cédula ya está registrada");
        }
        
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
            throw new AuthException("CEDULA_DUPLICADA", 
                    "La cédula ya está registrada en el sistema.");
        }
        
        // 2. Validar mayoría de edad
        LocalDate fechaNac = LocalDate.parse(request.getFechaNacimiento());
        if (Period.between(fechaNac, LocalDate.now()).getYears() < 18) {
            throw new AuthException("VALIDATION_ERROR", 
                    "El paciente debe ser mayor de edad.");
        }
        
        // 3. Crear paciente
<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        PersonaPaciente paciente = new PersonaPaciente();
        paciente.setCedula(request.getCedula());
        paciente.setPrimerNombre(request.getPrimerNombre());
        paciente.setSegundoNombre(request.getSegundoNombre());
        paciente.setPrimerApellido(request.getPrimerApellido());
        paciente.setSegundoApellido(request.getSegundoApellido());
<<<<<<< HEAD
<<<<<<< HEAD
        paciente.setCorreo(request.getCorreo());
        paciente.setPassword(passwordEncoder.encode(request.getPassword()));
        paciente.setTelefono(request.getTelefono());
        
        if (request.getFechaNacimiento() != null) {
            paciente.setFechaNacimiento(LocalDate.parse(request.getFechaNacimiento()));
        }
        
        paciente.setDiscapacidad(request.getDiscapacidad() != null && 
                !request.getDiscapacidad().equalsIgnoreCase("ninguna"));
        paciente.setAuthProvider(request.getGoogleId() != null ? 
                PersonaPaciente.AuthProvider.GOOGLE : PersonaPaciente.AuthProvider.LOCAL);
        paciente.setGoogleId(request.getGoogleId());
        paciente.setAccountStatus(PersonaPaciente.AccountStatus.ACTIVE);
        paciente.setProfileCompleted(true);
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
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
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        
        paciente = pacienteRepository.save(paciente);
        return createAuthResponse(paciente);
    }
    
    /**
<<<<<<< HEAD
<<<<<<< HEAD
     * Registrar empleado con código
     */
    @Transactional
    public AuthResponse registerEmpleado(RegisterEmpleadoRequest request) {
        // Validar código (mantener igual)
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
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
     * Registrar empleado con código de credencial (DEPRECATED - usar createEmpleado)
     */
    @Transactional
    public AuthResponse registerEmpleado(RegisterEmpleadoRequest request) {
        // Validar código de credencial
<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        CredentialCode credentialCode = credentialCodeRepository
                .findByCodeAndIsActive(request.getCodigoCredencial(), true)
                .orElseThrow(() -> new RuntimeException("Código de credenciales inválido"));

<<<<<<< HEAD
<<<<<<< HEAD
        // VALIDACIÓN CRUZADA: Buscar en ambas tablas
        if (empleadoRepository.existsByCorreo(request.getCorreo()) || 
            pacienteRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado en el sistema");
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        // Validación cruzada
        if (empleadoRepository.existsByCorreo(request.getCorreo()) || 
            pacienteRepository.existsByCorreo(request.getCorreo())) {
            throw new AuthException("CORREO_DUPLICADO", 
                    "El correo ya está registrado en el sistema.");
<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        }
        
        if (empleadoRepository.existsByCedula(request.getCedula()) || 
            pacienteRepository.existsByCedula(request.getCedula())) {
<<<<<<< HEAD
<<<<<<< HEAD
            throw new RuntimeException("La cédula ya está registrada");
=======
            throw new AuthException("CEDULA_DUPLICADA", 
                    "La cédula ya está registrada en el sistema.");
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
            throw new AuthException("CEDULA_DUPLICADA", 
                    "La cédula ya está registrada en el sistema.");
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        }
        
        PersonaEmpleado empleado = new PersonaEmpleado();
        empleado.setCedula(request.getCedula());
        empleado.setPrimerNombre(request.getPrimerNombre());
        empleado.setSegundoNombre(request.getSegundoNombre());
        empleado.setPrimerApellido(request.getPrimerApellido());
        empleado.setSegundoApellido(request.getSegundoApellido());
<<<<<<< HEAD
<<<<<<< HEAD
        empleado.setCorreo(request.getCorreo());
=======
        empleado.setCorreo(request.getCorreo().toLowerCase());
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
        empleado.setCorreo(request.getCorreo().toLowerCase());
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        empleado.setPassword(passwordEncoder.encode(request.getPassword()));
        empleado.setTelefono(request.getTelefono());
        
        if (request.getFechaNacimiento() != null) {
            empleado.setFechaNacimiento(LocalDate.parse(request.getFechaNacimiento()));
        }
        
        empleado.setRol(credentialCode.getRol());
        empleado.setAuthProvider(request.getGoogleId() != null ? 
                PersonaEmpleado.AuthProvider.GOOGLE : PersonaEmpleado.AuthProvider.LOCAL);
        empleado.setGoogleId(request.getGoogleId());
        empleado.setAccountStatus(PersonaEmpleado.AccountStatus.ACTIVE);
        empleado.setProfileCompleted(true);
        
        empleado = empleadoRepository.save(empleado);
        
<<<<<<< HEAD
<<<<<<< HEAD
        // Incrementar contador
=======
        // Incrementar contador de usos del código
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
        // Incrementar contador de usos del código
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        credentialCode.setCurrentUses(credentialCode.getCurrentUses() + 1);
        credentialCodeRepository.save(credentialCode);
        
        return createAuthResponse(empleado);
    }
    
<<<<<<< HEAD
<<<<<<< HEAD
    @Transactional
    public AuthResponse completeProfile(CompleteProfileRequest request) {
        // 1. VALIDACIÓN DE SEGURIDAD: Verificar si la cédula ya existe en el sistema (en ambas tablas)
        // Pero excluimos al usuario mismo si ya tiene esa cédula (para permitir actualizaciones)
        boolean cedulaExiste = pacienteRepository.existsByCedula(request.getCedula()) || 
                              empleadoRepository.existsByCedula(request.getCedula());
        
        // 2. Buscar usuario por Google ID
        Optional<PersonaPaciente> pacienteOpt = pacienteRepository.findByGoogleId(request.getGoogleId());
        Optional<PersonaEmpleado> empleadoOpt = empleadoRepository.findByGoogleId(request.getGoogleId());
        
        if (pacienteOpt.isPresent()) {
            PersonaPaciente paciente = pacienteOpt.get();
            // Si intenta cambiar la cédula por una que ya existe en otro registro
            if (cedulaExiste && !request.getCedula().equals(paciente.getCedula())) {
                throw new RuntimeException("La cédula ya está registrada por otro usuario.");
            }
            actualizarDatosPaciente(paciente, request);
            paciente.setProfileCompleted(true);
            return createAuthResponse(pacienteRepository.save(paciente));
        }
        
        if (empleadoOpt.isPresent()) {
            PersonaEmpleado empleado = empleadoOpt.get();
            if (cedulaExiste && !request.getCedula().equals(empleado.getCedula())) {
                throw new RuntimeException("La cédula ya está registrada por otro usuario.");
            }
            actualizarDatosEmpleado(empleado, request);
            empleado.setProfileCompleted(true);
            return createAuthResponse(empleadoRepository.save(empleado));
        }
        
        // 3. Si el usuario NO existe en ninguna tabla, validar la cédula antes de crear uno nuevo
        if (cedulaExiste) {
            throw new RuntimeException("La cédula ya está vinculada a otra cuenta.");
        }

        if (request.getCodigoCredencial() != null && !request.getCodigoCredencial().isEmpty()) {
            return crearNuevoEmpleadoDesdeGoogle(request);
        } else {
            return crearNuevoPacienteDesdeGoogle(request);
        }
    }
    
    // ===== MÉTODOS AUXILIARES =====
    
    private AuthResponse createAuthResponse(PersonaPaciente paciente) {
        String accessToken = jwtTokenProvider.generateToken(
                paciente.getId(), "PACIENTE", "PACIENTE", paciente.getCorreo()
        );
        String refreshToken = jwtTokenProvider.generateRefreshToken(paciente.getId(), "PACIENTE");
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
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
                paciente.getCorreo()
        );
        String refreshToken = jwtTokenProvider.generateRefreshToken(
                paciente.getId(), 
                "PACIENTE"
        );
<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        
        UserInfo userInfo = new UserInfo(
                paciente.getId(),
                paciente.getCorreo(),
                paciente.getPrimerNombre() + " " + paciente.getPrimerApellido(),
                "PACIENTE",
                paciente.getAuthProvider().name(),
                paciente.getImagenPerfil(),
                paciente.getProfileCompleted()
        );
        
<<<<<<< HEAD
<<<<<<< HEAD
        return new AuthResponse(accessToken, refreshToken, 
                jwtTokenProvider.getExpirationInSeconds(), userInfo);
    }
    
    private AuthResponse createAuthResponse(PersonaEmpleado empleado) {
        String rolNombre = empleado.getRol().getNombreRol();
        String accessToken = jwtTokenProvider.generateToken(
                empleado.getIdPerEmpleado().longValue(), "EMPLEADO", rolNombre, empleado.getCorreo()
        );
        String refreshToken = jwtTokenProvider.generateRefreshToken(
                empleado.getIdPerEmpleado().longValue(), "EMPLEADO");
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        return new AuthResponse(
                accessToken, 
                refreshToken, 
                jwtTokenProvider.getExpirationInSeconds(), 
                userInfo
        );
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
                empleado.getCorreo()
        );
        String refreshToken = jwtTokenProvider.generateRefreshToken(
                empleado.getIdPerEmpleado().longValue(), 
                "EMPLEADO"
        );
<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        
        UserInfo userInfo = new UserInfo(
                empleado.getIdPerEmpleado().longValue(),
                empleado.getCorreo(),
                empleado.getPrimerNombre() + " " + empleado.getPrimerApellido(),
                rolNombre,
                empleado.getAuthProvider().name(),
                empleado.getPerfilEmpleadoImg(),
                empleado.getProfileCompleted()
        );
        
<<<<<<< HEAD
<<<<<<< HEAD
        return new AuthResponse(accessToken, refreshToken, 
                jwtTokenProvider.getExpirationInSeconds(), userInfo);
    }
    
    private ProfileIncompleteResponse createProfileIncompleteResponse(
            String googleId, String email, String name, String givenName, 
            String familyName, String pictureUrl) {
        
        GoogleUserData googleData = new GoogleUserData(
                googleId, email, name, givenName, familyName, pictureUrl);
        List<String> requiredFields = Arrays.asList("cedula", "fechaNacimiento", "telefono");
        
        return new ProfileIncompleteResponse(
                "Por favor completa tu perfil para continuar", googleData, requiredFields);
    }
    
    private void actualizarDatosPaciente(PersonaPaciente paciente, CompleteProfileRequest request) {
        paciente.setCedula(request.getCedula());
        paciente.setPrimerNombre(request.getPrimerNombre());
        paciente.setSegundoNombre(request.getSegundoNombre());
        paciente.setPrimerApellido(request.getPrimerApellido());
        paciente.setSegundoApellido(request.getSegundoApellido());
        paciente.setTelefono(request.getTelefono());
        paciente.setFechaNacimiento(LocalDate.parse(request.getFechaNacimiento()));
        
        if (request.getDiscapacidad() != null) {
            paciente.setDiscapacidad(!request.getDiscapacidad().equalsIgnoreCase("ninguna"));
        }
    }
    
    private void actualizarDatosEmpleado(PersonaEmpleado empleado, CompleteProfileRequest request) {
        empleado.setCedula(request.getCedula());
        empleado.setPrimerNombre(request.getPrimerNombre());
        empleado.setSegundoNombre(request.getSegundoNombre());
        empleado.setPrimerApellido(request.getPrimerApellido());
        empleado.setSegundoApellido(request.getSegundoApellido());
        empleado.setTelefono(request.getTelefono());
        empleado.setFechaNacimiento(LocalDate.parse(request.getFechaNacimiento()));
    }
    
    private AuthResponse crearNuevoPacienteDesdeGoogle(CompleteProfileRequest request) {
        // 1. Obtener correo (deberías intentar obtener el correo real de Google guardado previamente)
        String correo = pacienteRepository.findByGoogleId(request.getGoogleId())
                .map(PersonaPaciente::getCorreo)
                .orElseThrow(() -> new RuntimeException("Error: Correo no encontrado para este ID de Google"));

        // 2. VALIDACIÓN CRUZADA antes de guardar
        if (empleadoRepository.existsByCorreo(correo)) {
            throw new RuntimeException("Este correo de Google ya está vinculado a una cuenta de Empleado.");
        }
        // La cédula ya se validó en el método superior 'completeProfile', así que es seguro

        PersonaPaciente paciente = new PersonaPaciente();
        paciente.setGoogleId(request.getGoogleId());
        paciente.setCorreo(correo);
        actualizarDatosPaciente(paciente, request);
        paciente.setAuthProvider(PersonaPaciente.AuthProvider.GOOGLE);
        paciente.setAccountStatus(PersonaPaciente.AccountStatus.ACTIVE);
        paciente.setProfileCompleted(true);
        
        return createAuthResponse(pacienteRepository.save(paciente));
    }

    private AuthResponse crearNuevoEmpleadoDesdeGoogle(CompleteProfileRequest request) {
        CredentialCode credentialCode = credentialCodeRepository
                .findByCodeAndIsActive(request.getCodigoCredencial(), true)
                .orElseThrow(() -> new RuntimeException("Código de credenciales inválido"));

        String correo = empleadoRepository.findByGoogleId(request.getGoogleId())
                .map(PersonaEmpleado::getCorreo)
                .orElseThrow(() -> new RuntimeException("Error: Correo no encontrado para este ID de Google"));

        // VALIDACIÓN CRUZADA
        if (pacienteRepository.existsByCorreo(correo)) {
            throw new RuntimeException("Este correo de Google ya está vinculado a una cuenta de Paciente.");
        }

        PersonaEmpleado empleado = new PersonaEmpleado();
        empleado.setGoogleId(request.getGoogleId());
        empleado.setCorreo(correo);
        actualizarDatosEmpleado(empleado, request);
        empleado.setRol(credentialCode.getRol());
        empleado.setAuthProvider(PersonaEmpleado.AuthProvider.GOOGLE);
        empleado.setAccountStatus(PersonaEmpleado.AccountStatus.ACTIVE);
        empleado.setProfileCompleted(true);
        
        PersonaEmpleado saved = empleadoRepository.save(empleado);
        
        credentialCode.setCurrentUses(credentialCode.getCurrentUses() + 1);
        credentialCodeRepository.save(credentialCode);
        
        return createAuthResponse(saved);
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        return new AuthResponse(
                accessToken, 
                refreshToken, 
                jwtTokenProvider.getExpirationInSeconds(), 
                userInfo
        );
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
                pictureUrl
        );
        
        List<String> requiredFields = Arrays.asList(
                "cedula", 
                "fechaNacimiento", 
                "telefono"
        );
        
        return new ProfileIncompleteResponse(
                "Por favor completa tu perfil para continuar", 
                googleData, 
                requiredFields
        );
<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
    }
}