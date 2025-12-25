package ista.M4A2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    private String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
    
    @NotBlank(message = "El tipo de usuario es obligatorio")
    private String tipoUsuario; // "PACIENTE", "MEDICO", "ADMINISTRADOR"

    // Constructores
    public LoginRequest() {}

    public LoginRequest(String correo, String password, String tipoUsuario) {
        this.correo = correo;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters y Setters
    public String getCorreo() { 
        return correo; 
    }
    
    public void setCorreo(String correo) { 
        this.correo = correo; 
    }

    public String getPassword() { 
        return password; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }

    public String getTipoUsuario() { 
        return tipoUsuario; 
    }
    
    public void setTipoUsuario(String tipoUsuario) { 
        this.tipoUsuario = tipoUsuario; 
    }
}