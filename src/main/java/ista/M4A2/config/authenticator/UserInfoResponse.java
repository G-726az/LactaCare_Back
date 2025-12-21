package ista.M4A2.config.authenticator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

	private Long id;
    private String correo;
    private String nombreCompleto;
    private String rol;
    private String authProvider;
    private String imagenPerfil;
    private Boolean profileCompleted;
 // Constructor que soluciona el error "is undefined"
    public UserInfoResponse(Long id, String correo, String nombreCompleto, String rol, 
                            String authProvider, String imagenPerfil, Boolean profileCompleted) {
        this.id = id;
        this.correo = correo;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.authProvider = authProvider;
        this.imagenPerfil = imagenPerfil;
        this.profileCompleted = profileCompleted;
    }

    // Getters y Setters (Necesarios para que Spring cree el JSON)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getAuthProvider() { return authProvider; }
    public void setAuthProvider(String authProvider) { this.authProvider = authProvider; }
    public String getImagenPerfil() { return imagenPerfil; }
    public void setImagenPerfil(String imagenPerfil) { this.imagenPerfil = imagenPerfil; }
    public Boolean getProfileCompleted() { return profileCompleted; }
    public void setProfileCompleted(Boolean profileCompleted) { this.profileCompleted = profileCompleted; }
}