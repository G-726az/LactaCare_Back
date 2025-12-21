package ista.M4A2.config.authenticator;
import java.time.LocalDateTime;
import java.util.List;
//DTOS de respuesta para autenticacion
//DTO de respuesta para login exitoso
public class AuthResponse {

	private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long expiresIn; // Seconds
    private UserInfo userInfo;
    
    public AuthResponse(String accessToken, String refreshToken, Long expiresIn, UserInfo userInfo) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.userInfo = userInfo;
    }
    
    // Getters y Setters
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    
    public Long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(Long expiresIn) { this.expiresIn = expiresIn; }
    
    public UserInfo getUserInfo() { return userInfo; }
    public void setUserInfo(UserInfo userInfo) { this.userInfo = userInfo; }
}

/**
 * Información del usuario autenticado
 */
class UserInfo {
    
    private Long id;
    private String correo;
    private String nombreCompleto;
    private String rol; // PACIENTE, DOCTOR, ADMINISTRADOR
    private String authProvider; // LOCAL, GOOGLE
    private String imagenPerfil;
    private Boolean profileCompleted;
    
    public UserInfo() {}
    
    public UserInfo(Long id, String correo, String nombreCompleto, String rol, 
                   String authProvider, String imagenPerfil, Boolean profileCompleted) {
        this.id = id;
        this.correo = correo;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
        this.authProvider = authProvider;
        this.imagenPerfil = imagenPerfil;
        this.profileCompleted = profileCompleted;
    }
    
    // Getters y Setters
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

/**
 * Respuesta cuando el perfil está incompleto (OAuth first-time)
 */
class ProfileIncompleteResponse {
    
    private String status = "USER_PROFILE_INCOMPLETE";
    private String message;
    private GoogleUserData googleUserData;
    private List<String> requiredFields;
    
    public ProfileIncompleteResponse(String message, GoogleUserData googleUserData, List<String> requiredFields) {
        this.message = message;
        this.googleUserData = googleUserData;
        this.requiredFields = requiredFields;
    }
    
    // Getters y Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public GoogleUserData getGoogleUserData() { return googleUserData; }
    public void setGoogleUserData(GoogleUserData googleUserData) { this.googleUserData = googleUserData; }
    
    public List<String> getRequiredFields() { return requiredFields; }
    public void setRequiredFields(List<String> requiredFields) { this.requiredFields = requiredFields; }
}

/**
 * Datos del usuario obtenidos de Google
 */
class GoogleUserData {
    
    private String googleId;
    private String email;
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    
    public GoogleUserData() {}
    
    public GoogleUserData(String googleId, String email, String name, String givenName, 
                         String familyName, String picture) {
        this.googleId = googleId;
        this.email = email;
        this.name = name;
        this.givenName = givenName;
        this.familyName = familyName;
        this.picture = picture;
    }
    
    // Getters y Setters
    public String getGoogleId() { return googleId; }
    public void setGoogleId(String googleId) { this.googleId = googleId; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getGivenName() { return givenName; }
    public void setGivenName(String givenName) { this.givenName = givenName; }
    
    public String getFamilyName() { return familyName; }
    public void setFamilyName(String familyName) { this.familyName = familyName; }
    
    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }
}

/**
 * Respuesta genérica de éxito/error
 */
class MessageResponse {
    
    private String message;
    private String status;
    private LocalDateTime timestamp;
    
    public MessageResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
    
    public MessageResponse(String message, String status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters y Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}

/**
 * Respuesta de error
 */
class ErrorResponse {
    
    private String error;
    private String message;
    private String path;
    private Integer status;
    private LocalDateTime timestamp;
    
    public ErrorResponse(String error, String message, String path, Integer status) {
        this.error = error;
        this.message = message;
        this.path = path;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters y Setters
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
