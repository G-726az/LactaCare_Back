package ista.M4A2.models.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Servicio centralizado para envío de correos electrónicos
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envía correo de bienvenida a empleado recién creado con credenciales
     * temporales
     * 
     * @param to       Correo del empleado
     * @param nombre   Nombre completo del empleado
     * @param correo   Correo del empleado (para login)
     * @param password Contraseña temporal
     */
    public void sendWelcomeEmployeeEmail(String to, String nombre, String correo, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Bienvenido a LactaCare - Credenciales de Acceso");
        message.setText(
                "Estimado/a " + nombre + ",\n\n" +
                        "Bienvenido/a al sistema LactaCare.\n\n" +
                        "Se ha creado una cuenta para usted con las siguientes credenciales:\n\n" +
                        "Correo: " + correo + "\n" +
                        "Contraseña temporal: " + password + "\n\n" +
                        "IMPORTANTE: Por seguridad, deberá cambiar esta contraseña temporal en su primer inicio de sesión.\n\n"
                        +
                        "Para acceder al sistema:\n" +
                        "1. Ingrese a la aplicación LactaCare\n" +
                        "2. Use las credenciales proporcionadas\n" +
                        "3. El sistema le solicitará crear una nueva contraseña\n\n" +
                        "Si tiene alguna pregunta o problema, por favor contacte al administrador del sistema.\n\n" +
                        "Saludos cordiales,\n" +
                        "Equipo LactaCare");

        mailSender.send(message);
    }

    /**
     * Envía correo con enlace de restablecimiento de contraseña
     * 
     * @param to                Correo del usuario
     * @param resetLink         Enlace de restablecimiento
     * @param expirationMinutes Minutos de expiración del token
     */
    public void sendPasswordResetEmail(String to, String resetLink, int expirationMinutes) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Restablecimiento de contraseña - LactaCare");
        message.setText(
                "Ha solicitado restablecer su contraseña.\n\n" +
                        "Para continuar, haga clic en el siguiente enlace:\n" +
                        resetLink + "\n\n" +
                        "Este enlace expirará en " + expirationMinutes + " minutos.\n\n" +
                        "Si no solicitó este cambio, ignore este correo.\n\n" +
                        "Saludos,\n" +
                        "Equipo LactaCare");

        mailSender.send(message);
    }

    /**
     * Envía confirmación de cambio de contraseña exitoso
     * 
     * @param to     Correo del usuario
     * @param nombre Nombre del usuario
     */
    public void sendPasswordChangedConfirmation(String to, String nombre) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Contraseña actualizada - LactaCare");
        message.setText(
                "Estimado/a " + nombre + ",\n\n" +
                        "Le confirmamos que su contraseña ha sido actualizada exitosamente.\n\n" +
                        "Si usted no realizó este cambio, por favor contacte inmediatamente al administrador del sistema.\n\n"
                        +
                        "Saludos,\n" +
                        "Equipo LactaCare");

        mailSender.send(message);
    }

    /**
     * Envía correo genérico
     * 
     * @param to      Destinatario
     * @param subject Asunto
     * @param body    Cuerpo del mensaje
     */
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    /**
     * Envía código de verificación de 6 dígitos para recuperación de contraseña
     * 
     * @param to                Correo del usuario
     * @param code              Código de 6 dígitos
     * @param expirationMinutes Minutos de expiración del código
     */
    public void sendPasswordResetCode(String to, String code, int expirationMinutes) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Código de recuperación - LactaCare");
        message.setText(
                "Ha solicitado restablecer su contraseña.\\n\\n" +
                        "Su código de verificación es:\\n\\n" +
                        "    " + code + "\\n\\n" +
                        "Este código expirará en " + expirationMinutes + " minutos.\\n\\n" +
                        "Ingrese este código en la aplicación para continuar con el restablecimiento de su contraseña.\\n\\n"
                        +
                        "Si no solicitó este cambio, ignore este correo y su contraseña permanecerá sin cambios.\\n\\n"
                        +
                        "Por seguridad, nunca comparta este código con nadie.\\n\\n" +
                        "Saludos,\\n" +
                        "Equipo LactaCare");

        mailSender.send(message);
    }
}
