package ista.M4A2.config.authenticator;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;
    
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
     * Login tradicional con email y password
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse response = authenticationService.authenticateTraditional(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("INVALID_CREDENTIALS", "Credenciales incorrectas", "/api/auth/login", 401));
        }
    }
    
    /**
     * Registro de nuevo paciente
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
     * Registro de nuevo empleado (Doctor/Admin)
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
    
    /**
     * Health check del servicio de autenticación
     * GET /api/auth/health
     */
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(new MessageResponse("Authentication service is running", "SUCCESS"));
    }
}
