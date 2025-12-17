package ista.M4A2.models.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    
    @Column(name = "Cedula", nullable = false)
    private String cedula;
    
    @Column(name = "Primer_nombre")
    private String primerNombre;
    
    @Column(name = "Segundo_nombre")
    private String segundoNombre;
    
    @Column(name = "Primer_apellido")
    private String primerApellido;
    
    @Column(name = "Segundo_apellido")
    private String segundoApellido;
    
    @Column(name = "Correo")
    private String correo;
    
    @Column(name = "Telefono")
    private String telefono;
    
    @Column(name = "Fechanacimiento")
    private LocalDate fechaNacimiento;
    
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
    
    @OneToMany(mappedBy = "empleado")
    private List<Atencion> atenciones;
    
    // Constructores
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
    
    // Getters y Setters
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