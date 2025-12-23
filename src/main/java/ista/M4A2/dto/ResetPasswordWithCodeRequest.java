package ista.M4A2.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para restablecer contraseña usando token obtenido después de verificar
 * código.
 */
public class ResetPasswordWithCodeRequest {

    @NotBlank(message = "El token es obligatorio")
    private String resetToken;

    @NotBlank(message = "La nueva contraseña es obligatoria")
    private String newPassword;

    // Constructores
    public ResetPasswordWithCodeRequest() {
    }

    public ResetPasswordWithCodeRequest(String resetToken, String newPassword) {
        this.resetToken = resetToken;
        this.newPassword = newPassword;
    }

    // Getters y Setters
    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ResetPasswordWithCodeRequest{" +
                "resetToken='***'" +
                ", newPassword='***'" +
                '}';
    }
}
