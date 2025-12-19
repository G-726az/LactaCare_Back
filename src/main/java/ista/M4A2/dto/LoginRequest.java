package ista.M4A2.dto;

public class LoginRequest {
    private String cedula;
    private String password;
    private String tipoUsuario; // ADMINISTRADOR, MEDICO, PACIENTE

    public LoginRequest() {}

    public LoginRequest(String cedula, String password, String tipoUsuario) {
        this.cedula = cedula;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
    }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipoUsuario) { this.tipoUsuario = tipoUsuario; }
}
