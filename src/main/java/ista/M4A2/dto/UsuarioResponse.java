package ista.M4A2.dto;

public class UsuarioResponse {

	private Long id;
    private String cedula;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String rol;       // "ADMINISTRADOR", "DOCTOR", "PACIENTE"
    private String imagen;    // URL de la foto

    // Constructor completo
    public UsuarioResponse(Long id, String cedula, String nombreCompleto, 
                           String correo, String telefono, String rol, String imagen) {
        this.id = id;
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;
        this.imagen = imagen;
    }

    // Getters (necesarios para que Jackson genere el JSON)
    public Long getId() { return id; }
    public String getCedula() { return cedula; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public String getRol() { return rol; }
    public String getImagen() { return imagen; }
}
