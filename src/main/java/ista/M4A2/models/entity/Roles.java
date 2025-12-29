package ista.M4A2.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Roles")
public class Roles implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_roles")
    private Integer idRoles;
    
    @Column(name = "Nombre_rol", nullable = false)
    private String nombreRol;
    
    @OneToMany(mappedBy = "rol")
    @JsonIgnore  // Evita referencias circulares en JSON
    private List<PersonaEmpleado> empleados;
    
    // Constructores
    public Roles() {
    }
    
    public Roles(Integer idRoles, String nombreRol) {
        this.idRoles = idRoles;
        this.nombreRol = nombreRol;
    }
    
    // Getters y Setters
    public Integer getIdRoles() {
        return idRoles;
    }
    
    public void setIdRoles(Integer idRoles) {
        this.idRoles = idRoles;
    }
    
    public String getNombreRol() {
        return nombreRol;
    }
    
    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
    
    public List<PersonaEmpleado> getEmpleados() {
        return empleados;
    }
    
    public void setEmpleados(List<PersonaEmpleado> empleados) {
        this.empleados = empleados;
    }
}