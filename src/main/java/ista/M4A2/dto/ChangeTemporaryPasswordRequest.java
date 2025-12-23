package ista.M4A2.dto;

import jakarta.validation.constraints.*;

/**
 * DTO para cambio de contraseña temporal de empleados
 */
public class ChangeTemporaryPasswordRequest {

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    private String correo;

    @NotBlank(message = "La contraseña temporal es obligatoria")
    private String temporaryPassword;

    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String newPassword;

    // Constructores
    public ChangeTemporaryPasswordRequest() {
    }

    public ChangeTemporaryPasswordRequest(String correo, String temporaryPassword, String newPassword) {
        this.correo = correo;
        this.temporaryPassword = temporaryPassword;
        this.newPassword = newPassword;
    }

    // Getters y Setters
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTemporaryPassword() {
        return temporaryPassword;
    }

    public void setTemporaryPassword(String temporaryPassword) {
        this.temporaryPassword = temporaryPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
