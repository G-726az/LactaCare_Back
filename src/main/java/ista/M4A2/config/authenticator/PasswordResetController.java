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

import org.springframework.mail.SimpleMailMessage;
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
				return ResponseEntity.ok().body(
						new ApiResponse(true, "Si el correo existe, se ha enviado un enlace de restablecimiento"));
			}
			// Generar token y guardar
			String token = UUID.randomUUID().toString();
			paciente.setResetToken(token); 
			paciente.setTokenExpiration(LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_MINUTES)); // Establecer expiración
			personaPacienteService.save(paciente);
			// Enviar correo
			String resetLink = "http://localhost:8080/api/auth/reset-password?token=" + token; //
			sendResetEmail(paciente.getCorreo(), resetLink); 
			return ResponseEntity.ok()
					.body(new ApiResponse(true, "Correos de restablecimiento enviados si el correo existe"));
		} catch (Exception e) {
			// Manejo de errores
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(false, "Error al procesar la solicitud"));
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

	private void sendResetEmail(String to, String resetLink) {
		// Configurar y enviar el correo
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Restablecimiento de contraseña");
		message.setText(
				"Ha solicitado restablecer su contraseña.\n\n" + "Para continuar, haga clic en el siguiente enlace:\n"
						+ resetLink + "\n\n" + "Este enlace expirará en " + TOKEN_EXPIRATION_MINUTES + " minutos.\n\n"
						+ "Si no solicitó este cambio, ignore este correo.\n\n" + "Saludos,\n" + "Equipo de Soporte");

		mailSender.send(message);
	}

}
