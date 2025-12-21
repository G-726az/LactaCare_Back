package ista.M4A2.models.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Persona_Empleado")
public class PersonaEmpleado implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_PerEmpleado")
    private Integer idPerEmpleado;
    
    @Column(name = "Perfil_empleado_img")
    private String perfilEmpleadoImg;
    
    @Column(name = "Cedula", nullable = false, unique = true, length = 13)
    private String cedula;
    
    @Column(name = "Primer_nombre")
    private String primerNombre;
    
    @Column(name = "Segundo_nombre")
    private String segundoNombre;
    
    @Column(name = "Primer_apellido")
    private String primerApellido;
    
    @Column(name = "Segundo_apellido")
    private String segundoApellido;
    
    @Column(name = "Correo", unique = true)
    private String correo;
    
    @Column(name = "Telefono")
    private String telefono;
    
    @Column(name = "Fechanacimiento")
    private LocalDate fechaNacimiento;
    
    // ===== CAMPOS DE AUTENTICACIÓN =====
    
    @Column(name = "Password", length = 100)
    private String password;
    
    @Column(name = "google_id", unique = true)
    private String googleId;
    
    @Column(name = "auth_provider", length = 20)
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider = AuthProvider.LOCAL;
    
    @Column(name = "account_status", length = 20)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;
    
    @Column(name = "profile_completed")
    private Boolean profileCompleted = false;
    
    // ===== CAMPOS DE RECUPERACIÓN DE CONTRASEÑA =====
    
    @Column(name = "reset_token")
    private String resetToken;
    
    @Column(name = "token_expiration")
    private LocalDateTime tokenExpiration;
    
    // ===== RELACIONES =====
    
    @ManyToOne
    @JoinColumn(name = "Rol_empleado", referencedColumnName = "Id_roles")
    private Roles rol;
    
    @ManyToMany
    @JoinTable(
        name = "Empleado_Horarios",
        joinColumns = @JoinColumn(name = "Id_PerEmpleado"),
        inverseJoinColumns = @JoinColumn(name = "Id_Horario")
    )
    private List<Horarios> horarios = new ArrayList<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "empleado")
    private List<Atencion> atenciones;
    
    // ===== ENUMS =====
    
    public enum AuthProvider {
        LOCAL, GOOGLE
    }
    
    public enum AccountStatus {
        ACTIVE, SUSPENDED, PENDING, INCOMPLETE
    }
    
    // ===== CONSTRUCTORES =====
    
    public PersonaEmpleado() {
    }
    
    public PersonaEmpleado(Integer idPerEmpleado, String cedula, String primerNombre, 
                          String primerApellido, String correo, String telefono, 
                          LocalDate fechaNacimiento) {
        this.idPerEmpleado = idPerEmpleado;
        this.cedula = cedula;
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }
    
    // ===== GETTERS Y SETTERS =====
    
    public Integer getIdPerEmpleado() {
        return idPerEmpleado;
    }
    
    public void setIdPerEmpleado(Integer idPerEmpleado) {
        this.idPerEmpleado = idPerEmpleado;
    }
    
    public String getPerfilEmpleadoImg() {
        return perfilEmpleadoImg;
    }
    
    public void setPerfilEmpleadoImg(String perfilEmpleadoImg) {
        this.perfilEmpleadoImg = perfilEmpleadoImg;
    }
    
    public String getCedula() {
        return cedula;
    }
    
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public String getPrimerNombre() {
        return primerNombre;
    }
    
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }
    
    public String getSegundoNombre() {
        return segundoNombre;
    }
    
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }
    
    public String getPrimerApellido() {
        return primerApellido;
    }
    
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }
    
    public String getSegundoApellido() {
        return segundoApellido;
    }
    
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getGoogleId() {
        return googleId;
    }
    
    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
    
    public AuthProvider getAuthProvider() {
        return authProvider;
    }
    
    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }
    
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
    
    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
    
    public Boolean getProfileCompleted() {
        return profileCompleted;
    }
    
    public void setProfileCompleted(Boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }
    
    public String getResetToken() {
        return resetToken;
    }
    
    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
    
    public LocalDateTime getTokenExpiration() {
        return tokenExpiration;
    }
    
    public void setTokenExpiration(LocalDateTime tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }
    
    public Roles getRol() {
        return rol;
    }
    
    public void setRol(Roles rol) {
        this.rol = rol;
    }
    
    public List<Horarios> getHorarios() {
        return horarios;
    }
    
    public void setHorarios(List<Horarios> horarios) {
        this.horarios = horarios;
    }
    
    public List<Atencion> getAtenciones() {
        return atenciones;
    }
    
    public void setAtenciones(List<Atencion> atenciones) {
        this.atenciones = atenciones;
    }
}