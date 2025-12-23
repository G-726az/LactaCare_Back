package ista.M4A2.dto;

/**
 * DTO para respuesta de verificación de cuenta
 */
public class AccountVerificationResponse {

    private Boolean existe;
    private String metodoAuth; // "LOCAL", "GOOGLE", "AMBOS"
    private String rol; // "PACIENTE", "DOCTOR", "ADMINISTRADOR"
    private Boolean requirePasswordChange;

    // Constructores
    public AccountVerificationResponse() {
    }

    public AccountVerificationResponse(Boolean existe, String metodoAuth, String rol, Boolean requirePasswordChange) {
        this.existe = existe;
        this.metodoAuth = metodoAuth;
        this.rol = rol;
        this.requirePasswordChange = requirePasswordChange;
    }

    // Constructor para cuenta no existente
    public static AccountVerificationResponse notFound() {
        return new AccountVerificationResponse(false, null, null, null);
    }

    // Getters y Setters
    public Boolean getExiste() {
        return existe;
    }

    public void setExiste(Boolean existe) {
        this.existe = existe;
    }

    public String getMetodoAuth() {
        return metodoAuth;
    }

    public void setMetodoAuth(String metodoAuth) {
        this.metodoAuth = metodoAuth;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Boolean getRequirePasswordChange() {
        return requirePasswordChange;
    }

    public void setRequirePasswordChange(Boolean requirePasswordChange) {
        this.requirePasswordChange = requirePasswordChange;
    }
}
