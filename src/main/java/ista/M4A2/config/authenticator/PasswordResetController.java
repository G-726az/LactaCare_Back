package ista.M4A2.config.authenticator;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ista.M4A2.dto.ApiResponse;
import ista.M4A2.dto.ResetPasswordRequest;
import ista.M4A2.models.entity.PersonaPaciente;
import ista.M4A2.models.services.serv.IPersonaPacienteService;
import ista.M4A2.verificaciones.PasswordValidator;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
	private static final int TOKEN_EXPIRATION_MINUTES = 15;

	@Autowired
	private IPersonaPacienteService personaPacienteService;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ista.M4A2.models.services.impl.EmailService emailService;

	@PostMapping("/forgot-password")
	public ResponseEntity<ApiResponse> forgotPassword(@RequestParam String correo) {
		try {
			// Validar formato de correo
			if (!EMAIL_PATTERN.matcher(correo).matches()) {
				return ResponseEntity.badRequest().body(new ApiResponse(false, "Formato de correo inválido"));
			}
			// Buscar paciente por correo
			PersonaPaciente paciente = personaPacienteService.findByCorreo(correo);
			if (paciente == null) {
				// Mensaje específico indicando que el correo no existe
				return ResponseEntity.badRequest().body(
						new ApiResponse(false, "Correo de restablecimiento no enviado, el correo no existe."));
			}

			// Validar si es cuenta solo-Google (sin contraseña)
			if (paciente.getAuthProvider() == PersonaPaciente.AuthProvider.GOOGLE
					&& paciente.getPassword() == null) {
				return ResponseEntity.badRequest().body(
						new ApiResponse(false,
								"Esta cuenta usa Google para iniciar sesión. Por favor use 'Iniciar sesión con Google'."));
			}

			// Generar código de 6 dígitos
			String resetCode = ista.M4A2.verificaciones.CodeGenerator.generateResetCode();
			paciente.setResetCode(resetCode);
			paciente.setResetCodeExpiration(LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_MINUTES));
			personaPacienteService.save(paciente);

			// Enviar correo con código
			emailService.sendPasswordResetCode(paciente.getCorreo(), resetCode, TOKEN_EXPIRATION_MINUTES);

			return ResponseEntity.ok()
					.body(new ApiResponse(true, "Código de recuperación enviado a su correo. Válido por "
							+ TOKEN_EXPIRATION_MINUTES + " minutos."));
		} catch (Exception e) {
			// Manejo de errores
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(false, "Error al procesar la solicitud"));
		}
	}

	@PostMapping("/verify-reset-code")
	public ResponseEntity<?> verifyResetCode(@RequestBody ista.M4A2.dto.VerifyCodeRequest request) {
		try {
			// Buscar paciente
			PersonaPaciente paciente = personaPacienteService.findByCorreo(request.getCorreo());
			if (paciente == null) {
				return ResponseEntity.badRequest()
						.body(new ApiResponse(false, "Correo no encontrado"));
			}

			// Verificar código
			if (paciente.getResetCode() == null || !request.getCodigo().equals(paciente.getResetCode())) {
				return ResponseEntity.badRequest()
						.body(new ApiResponse(false, "Código inválido"));
			}

			// Verificar expiración
			if (paciente.getResetCodeExpiration().isBefore(LocalDateTime.now())) {
				paciente.setResetCode(null);
				paciente.setResetCodeExpiration(null);
				personaPacienteService.save(paciente);
				return ResponseEntity.badRequest()
						.body(new ApiResponse(false, "Código expirado. Solicite uno nuevo."));
			}

			// Generar token temporal para el siguiente paso
			String resetToken = UUID.randomUUID().toString();
			paciente.setResetToken(resetToken);
			paciente.setTokenExpiration(LocalDateTime.now().plusMinutes(5)); // 5 min para cambiar password
			personaPacienteService.save(paciente);

			return ResponseEntity.ok()
					.body(new ista.M4A2.dto.VerifyCodeResponse(true, "Código válido", resetToken));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(false, "Error al verificar código"));
		}
	}

	@PostMapping("/reset-password-with-code")
	public ResponseEntity<ApiResponse> resetPasswordWithCode(
			@RequestBody ista.M4A2.dto.ResetPasswordWithCodeRequest request) {
		try {
			// Buscar por token
			PersonaPaciente paciente = personaPacienteService.findByResetToken(request.getResetToken());
			if (paciente == null) {
				return ResponseEntity.badRequest()
						.body(new ApiResponse(false, "Token inválido"));
			}

			// Verificar expiración del token
			if (paciente.getTokenExpiration().isBefore(LocalDateTime.now())) {
				return ResponseEntity.badRequest()
						.body(new ApiResponse(false, "Token expirado. Inicie el proceso nuevamente."));
			}

			// Validar nueva contraseña
			String validationMsg = PasswordValidator.validatePassword(request.getNewPassword());
			if (validationMsg != null) {
				return ResponseEntity.badRequest()
						.body(new ApiResponse(false, validationMsg));
			}

			// Actualizar contraseña
			paciente.setPassword(passwordEncoder.encode(request.getNewPassword()));
			paciente.setResetCode(null);
			paciente.setResetCodeExpiration(null);
			paciente.setResetToken(null);
			paciente.setTokenExpiration(null);
			personaPacienteService.save(paciente);

			return ResponseEntity.ok()
					.body(new ApiResponse(true, "Contraseña restablecida exitosamente"));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(false, "Error al restablecer contraseña"));
		}
	}

	@PostMapping("/reset-password")
	public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest request) {

		try {
			// Validaciones básicas
			if (request.getToken() == null || request.getToken().isEmpty()) {
				return ResponseEntity.badRequest().body(new ApiResponse(false, "Token es obligatorio"));
			}
			// Validar nueva contraseña
			if (request.getNewPassword() == null || request.getNewPassword().isEmpty()) {
				return ResponseEntity.badRequest().body(new ApiResponse(false, "Nueva contraseña es obligatoria"));
			}
			String passwordValidationMsg = PasswordValidator.validatePassword(request.getNewPassword());
			if (passwordValidationMsg != null) {
				return ResponseEntity.badRequest().body(new ApiResponse(false, passwordValidationMsg));
			}
			// Buscar paciente por token
			PersonaPaciente paciente = personaPacienteService.findByResetToken(request.getToken());
			if (paciente == null) {
				return ResponseEntity.badRequest().body(new ApiResponse(false, "Token inválido"));
			}
			// Verificar expiración del token
			if (paciente.getTokenExpiration().isBefore(LocalDateTime.now())) {
				paciente.setResetToken(null);
				paciente.setTokenExpiration(null);
				personaPacienteService.save(paciente);
				return ResponseEntity.badRequest().body(new ApiResponse(false, "Token ha expirado"));
			}
			// Actualizar contraseña
			paciente.setPassword(passwordEncoder.encode(request.getNewPassword()));
			paciente.setResetToken(null);
			paciente.setTokenExpiration(null);
			personaPacienteService.save(paciente);

			return ResponseEntity.ok(new ApiResponse(true, "Contraseña restablecida exitosamente"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(false, "Error al procesar la solicitud"));
		}
	}

}
