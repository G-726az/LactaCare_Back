package ista.M4A2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO para verificar código de recuperación de contraseña.
 */
public class VerifyCodeRequest {

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Formato de correo inválido")
    private String correo;

    @NotBlank(message = "El código es obligatorio")
    @Pattern(regexp = "^\\d{6}$", message = "El código debe ser de 6 dígitos numéricos")
    private String codigo;

    // Constructores
    public VerifyCodeRequest() {
    }

    public VerifyCodeRequest(String correo, String codigo) {
        this.correo = correo;
        this.codigo = codigo;
    }

    // Getters y Setters
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "VerifyCodeRequest{" +
                "correo='" + correo + '\'' +
                ", codigo='***'" + // No mostrar código en logs
                '}';
    }
}
