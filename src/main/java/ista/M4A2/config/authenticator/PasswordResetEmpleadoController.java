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
import ista.M4A2.models.entity.PersonaEmpleado;
import ista.M4A2.models.services.serv.PersonaEmpleadoService;
import ista.M4A2.verificaciones.PasswordValidator;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/auth/empleado")
public class PasswordResetEmpleadoController {

	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
	private static final int TOKEN_EXPIRATION_MINUTES = 15;

	@Autowired
	private PersonaEmpleadoService personaEmpleadoService;

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
			
			// Buscar empleado por correo
			PersonaEmpleado empleado = null;
			try {
				empleado = personaEmpleadoService.obtenerPorCorreo(correo);
			} catch (RuntimeException e) {
				// Por seguridad, no revelamos si el correo existe o no
				return ResponseEntity.ok().body(
						new ApiResponse(true, "Si el correo existe, se ha enviado un enlace de restablecimiento"));
			}
			
			// Generar token y guardar
			String token = UUID.randomUUID().toString();
			empleado.setResetToken(token);
			empleado.setTokenExpiration(LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_MINUTES));
			personaEmpleadoService.guardar(empleado);
			
			// Enviar correo
			String resetLink = "http://localhost:8080/api/auth/empleado/reset-password?token=" + token;
			sendResetEmail(empleado.getCorreo(), resetLink, empleado.getPrimerNombre());
			
			return ResponseEntity.ok()
					.body(new ApiResponse(true, "Correo de restablecimiento enviado si el correo existe"));
		} catch (Exception e) {
			e.printStackTrace();
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
			
			// Buscar empleado por token
			PersonaEmpleado empleado = personaEmpleadoService.findByResetToken(request.getToken());
			if (empleado == null) {
				return ResponseEntity.badRequest().body(new ApiResponse(false, "Token inválido"));
			}
			
			// Verificar expiración del token
			if (empleado.getTokenExpiration().isBefore(LocalDateTime.now())) {
				empleado.setResetToken(null);
				empleado.setTokenExpiration(null);
				personaEmpleadoService.guardar(empleado);
				return ResponseEntity.badRequest().body(new ApiResponse(false, "Token ha expirado"));
			}
			
			// Actualizar contraseña
			empleado.setPassword(passwordEncoder.encode(request.getNewPassword()));
			empleado.setResetToken(null);
			empleado.setTokenExpiration(null);
			personaEmpleadoService.guardar(empleado);

			return ResponseEntity.ok(new ApiResponse(true, "Contraseña restablecida exitosamente"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse(false, "Error al procesar la solicitud"));
		}
	}

	private void sendResetEmail(String to, String resetLink, String nombreEmpleado) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Restablecimiento de contraseña - LactaCare");
		message.setText(
				"Estimado/a " + nombreEmpleado + ",\n\n" +
				"Ha solicitado restablecer su contraseña de empleado en LactaCare.\n\n" + 
				"Para continuar, haga clic en el siguiente enlace:\n" + resetLink + "\n\n" + 
				"Este enlace expirará en " + TOKEN_EXPIRATION_MINUTES + " minutos.\n\n" +
				"Si no solicitó este cambio, ignore este correo y su contraseña permanecerá sin cambios.\n\n" + 
				"Por seguridad, este enlace solo puede usarse una vez.\n\n" +
				"Saludos,\n" + 
				"Equipo LactaCare");

		mailSender.send(message);
	}
}