package ista.M4A2.dto;

/**
 * DTO para respuesta de creación de empleado
 */
public class CreateEmpleadoResponse {

    private Integer id;
    private String correo;
    private String nombreCompleto;
    private String rol;
    private String temporaryPassword;
    private String mensaje;

    // Constructores
    public CreateEmpleadoResponse() {
    }

    public CreateEmpleadoResponse(Integer id, String correo, String nombreCompleto,
            String rol, String temporaryPassword, String mensaje) {
        this.id = id;
        this.correo = correo;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.temporaryPassword = temporaryPassword;
        this.mensaje = mensaje;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getTemporaryPassword() {
        return temporaryPassword;
    }

    public void setTemporaryPassword(String temporaryPassword) {
        this.temporaryPassword = temporaryPassword;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
