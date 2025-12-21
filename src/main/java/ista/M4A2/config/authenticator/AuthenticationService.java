package ista.M4A2.config.authenticator;
import ista.M4A2.models.entity.*;
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
    
    @Value("${google.auth.valid-audiences}")
    private String validAudiences;
    
    private GoogleIdTokenVerifier verifier;
    
    /**
     * Inicializa el verificador de tokens de Google
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
    
    /**
     * Autentica con Google OAuth2
     */
    @Transactional
    public Object authenticateWithGoogle(GoogleAuthRequest request) throws Exception {
        // 1. Verificar el token de Google
        GoogleIdToken idToken = getVerifier().verify(request.getIdToken());
        
        if (idToken == null) {
            throw new RuntimeException("Token de Google inválido");
        }
        
        Payload payload = idToken.getPayload();
        String googleId = payload.getSubject();
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String givenName = (String) payload.get("given_name");
        String familyName = (String) payload.get("family_name");
        String pictureUrl = (String) payload.get("picture");
        
        // 2. Buscar usuario por google_id
        Optional<PersonaPaciente> pacienteOpt = pacienteRepository.findByGoogleId(googleId);
        Optional<PersonaEmpleado> empleadoOpt = empleadoRepository.findByGoogleId(googleId);
        
        // 3. Si existe, generar JWT
        if (pacienteOpt.isPresent()) {
            PersonaPaciente paciente = pacienteOpt.get();
            if (!paciente.getProfileCompleted()) {
                return createProfileIncompleteResponse(googleId, email, name, givenName, familyName, pictureUrl);
            }
            return createAuthResponse(paciente);
        }
        
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
                paciente.setImagenPerfil(pictureUrl);
            }
            pacienteRepository.save(paciente);
            return createAuthResponse(paciente);
        }
        
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
        }
        
        if (pacienteRepository.existsByCedula(request.getCedula()) || 
            empleadoRepository.existsByCedula(request.getCedula())) {
            throw new RuntimeException("La cédula ya está registrada");
        }
        
        PersonaPaciente paciente = new PersonaPaciente();
        paciente.setCedula(request.getCedula());
        paciente.setPrimerNombre(request.getPrimerNombre());
        paciente.setSegundoNombre(request.getSegundoNombre());
        paciente.setPrimerApellido(request.getPrimerApellido());
        paciente.setSegundoApellido(request.getSegundoApellido());
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
        
        paciente = pacienteRepository.save(paciente);
        return createAuthResponse(paciente);
    }
    
    /**
     * Registrar empleado con código
     */
    @Transactional
    public AuthResponse registerEmpleado(RegisterEmpleadoRequest request) {
        // Validar código (mantener igual)
        CredentialCode credentialCode = credentialCodeRepository
                .findByCodeAndIsActive(request.getCodigoCredencial(), true)
                .orElseThrow(() -> new RuntimeException("Código de credenciales inválido"));

        // VALIDACIÓN CRUZADA: Buscar en ambas tablas
        if (empleadoRepository.existsByCorreo(request.getCorreo()) || 
            pacienteRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado en el sistema");
        }
        
        if (empleadoRepository.existsByCedula(request.getCedula()) || 
            pacienteRepository.existsByCedula(request.getCedula())) {
            throw new RuntimeException("La cédula ya está registrada");
        }
        
        PersonaEmpleado empleado = new PersonaEmpleado();
        empleado.setCedula(request.getCedula());
        empleado.setPrimerNombre(request.getPrimerNombre());
        empleado.setSegundoNombre(request.getSegundoNombre());
        empleado.setPrimerApellido(request.getPrimerApellido());
        empleado.setSegundoApellido(request.getSegundoApellido());
        empleado.setCorreo(request.getCorreo());
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
        
        // Incrementar contador
        credentialCode.setCurrentUses(credentialCode.getCurrentUses() + 1);
        credentialCodeRepository.save(credentialCode);
        
        return createAuthResponse(empleado);
    }
    
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
        
        UserInfo userInfo = new UserInfo(
                paciente.getId(),
                paciente.getCorreo(),
                paciente.getPrimerNombre() + " " + paciente.getPrimerApellido(),
                "PACIENTE",
                paciente.getAuthProvider().name(),
                paciente.getImagenPerfil(),
                paciente.getProfileCompleted()
        );
        
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
        
        UserInfo userInfo = new UserInfo(
                empleado.getIdPerEmpleado().longValue(),
                empleado.getCorreo(),
                empleado.getPrimerNombre() + " " + empleado.getPrimerApellido(),
                rolNombre,
                empleado.getAuthProvider().name(),
                empleado.getPerfilEmpleadoImg(),
                empleado.getProfileCompleted()
        );
        
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
    }
}