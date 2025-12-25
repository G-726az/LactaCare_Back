package ista.M4A2.models.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "personas_pacientes")
public class PersonaPaciente implements Serializable {

    private static final long serialVersionUID = 1L;
<<<<<<< HEAD
<<<<<<< HEAD
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "cedula", unique = true, length = 13)
    private String cedula;
    
    @Column(name = "imagen_perfil")
    private String imagenPerfil;
    
    @Column(name = "primer_nombre", length = 50)
    private String primerNombre;
    
    @Column(name = "segundo_nombre", length = 50)
    private String segundoNombre;
    
    @Column(name = "primer_apellido", length = 50)
    private String primerApellido;
    
    @Column(name = "segundo_apellido", length = 50)
    private String segundoApellido;
    
    @Column(name = "correo", unique = true, length = 100)
    private String correo;
    
    @Column(name = "telefono", length = 15)
    private String telefono;
    
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    @Column(name = "discapacidad", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean discapacidad;
    
    // ===== CAMPOS DE AUTENTICACIÓN =====
    
    @Column(name = "password", length = 100)
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
    
    // Unión con la entidad Reservas, una PersonaPaciente puede tener varias Reservas
    @OneToMany(mappedBy = "personaPaciente")
    @JsonIgnoreProperties("personaPaciente")
    private List<Reserva> reservas;
    
    // Unión con la entidad ContactoEmergencia, una PersonaPaciente puede tener varios Contactos de Emergencia
    @OneToMany(mappedBy = "personaPaciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("personaPaciente")
    private List<ContactoEmergencia> contactosEmergencia;
    
    // Unión con la entidad Lactante, una PersonaPaciente puede tener varios Lactantes
    @OneToMany(mappedBy = "personaPaciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("personaPaciente")
    private List<Lactante> lactantes;

    // ===== ENUMS =====
    
    public enum AuthProvider {
        LOCAL, GOOGLE
    }
    
    public enum AccountStatus {
        ACTIVE, SUSPENDED, PENDING, INCOMPLETE
    }

    // ===== GETTERS Y SETTERS =====
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    
    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
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
    
    public boolean isDiscapacidad() {
        return discapacidad;
    }
    
    public void setDiscapacidad(boolean discapacidad) {
        this.discapacidad = discapacidad;
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

=======

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cedula", unique = true, length = 13)
    private String cedula;

    @Column(name = "imagen_perfil")
    private String imagenPerfil;

    @Column(name = "primer_nombre", length = 50)
    private String primerNombre;

    @Column(name = "segundo_nombre", length = 50)
    private String segundoNombre;

    @Column(name = "primer_apellido", length = 50)
    private String primerApellido;

    @Column(name = "segundo_apellido", length = 50)
    private String segundoApellido;

    @Column(name = "correo", unique = true, length = 100)
    private String correo;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "discapacidad", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean discapacidad;

    // ===== CAMPOS DE AUTENTICACIÓN =====

    @Column(name = "password", length = 100)
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

    @Column(name = "reset_code", length = 6)
    private String resetCode;

    @Column(name = "reset_code_expiration")
    private LocalDateTime resetCodeExpiration;

    // ===== RELACIONES =====

    // Unión con la entidad Reservas, una PersonaPaciente puede tener varias
    // Reservas
    @OneToMany(mappedBy = "personaPaciente")
    @JsonIgnoreProperties("personaPaciente")
    private List<Reserva> reservas;

=======

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cedula", unique = true, length = 13)
    private String cedula;

    @Column(name = "imagen_perfil")
    private String imagenPerfil;

    @Column(name = "primer_nombre", length = 50)
    private String primerNombre;

    @Column(name = "segundo_nombre", length = 50)
    private String segundoNombre;

    @Column(name = "primer_apellido", length = 50)
    private String primerApellido;

    @Column(name = "segundo_apellido", length = 50)
    private String segundoApellido;

    @Column(name = "correo", unique = true, length = 100)
    private String correo;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "discapacidad", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean discapacidad;

    // ===== CAMPOS DE AUTENTICACIÓN =====

    @Column(name = "password", length = 100)
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

    @Column(name = "reset_code", length = 6)
    private String resetCode;

    @Column(name = "reset_code_expiration")
    private LocalDateTime resetCodeExpiration;

    // ===== RELACIONES =====

    // Unión con la entidad Reservas, una PersonaPaciente puede tener varias
    // Reservas
    @OneToMany(mappedBy = "personaPaciente")
    @JsonIgnoreProperties("personaPaciente")
    private List<Reserva> reservas;

>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
    // Unión con la entidad ContactoEmergencia, una PersonaPaciente puede tener
    // varios Contactos de Emergencia
    @OneToMany(mappedBy = "personaPaciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("personaPaciente")
    private List<ContactoEmergencia> contactosEmergencia;

    // Unión con la entidad Lactante, una PersonaPaciente puede tener varios
    // Lactantes
    @OneToMany(mappedBy = "personaPaciente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("personaPaciente")
    private List<Lactante> lactantes;

    // ===== ENUMS =====

    public enum AuthProvider {
        LOCAL, GOOGLE
    }

    public enum AccountStatus {
        ACTIVE, SUSPENDED, PENDING, INCOMPLETE
    }

    // ===== GETTERS Y SETTERS =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
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

    public boolean isDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(boolean discapacidad) {
        this.discapacidad = discapacidad;
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

<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Boolean getProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(Boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }
<<<<<<< HEAD
<<<<<<< HEAD
    
    public String getResetToken() {
        return resetToken;
    }
    
    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
    
    public LocalDateTime getTokenExpiration() {
        return tokenExpiration;
    }
    
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getTokenExpiration() {
        return tokenExpiration;
    }

<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
    public void setTokenExpiration(LocalDateTime tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public List<ContactoEmergencia> getContactosEmergencia() {
        return contactosEmergencia;
    }

    public void setContactosEmergencia(List<ContactoEmergencia> contactosEmergencia) {
        this.contactosEmergencia = contactosEmergencia;
    }

    public List<Lactante> getLactantes() {
        return lactantes;
    }

    public void setLactantes(List<Lactante> lactantes) {
        this.lactantes = lactantes;
    }
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)

    public String getResetCode() {
        return resetCode;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }

    public LocalDateTime getResetCodeExpiration() {
        return resetCodeExpiration;
    }

    public void setResetCodeExpiration(LocalDateTime resetCodeExpiration) {
        this.resetCodeExpiration = resetCodeExpiration;
    }
<<<<<<< HEAD
>>>>>>> 5ba0463 (Actualización backend: mejoras en controladores y modelos. Login y autenticaciones)
=======

    public Sala_Lactancia getSalaLactancia() {
        return salaLactancia;
    }

    public void setSalaLactancia(Sala_Lactancia salaLactancia) {
        this.salaLactancia = salaLactancia;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala_lactancia")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Sala_Lactancia salaLactancia;
    
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
}