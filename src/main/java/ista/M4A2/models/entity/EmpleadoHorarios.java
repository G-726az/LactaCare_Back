package ista.M4A2.models.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Empleado_Horarios")
public class EmpleadoHorarios implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private EmpleadoHorariosPK id;
    
    @ManyToOne
    @MapsId("idPerEmpleado")
    @JoinColumn(name = "Id_PerEmpleado", referencedColumnName = "Id_PerEmpleado")
    private PersonaEmpleado empleado;
    
    @ManyToOne
    @MapsId("idHorario")
    @JoinColumn(name = "Id_Horario", referencedColumnName = "Id_Horario")
    private Horarios horario;
    
    // Constructores
    public EmpleadoHorarios() {
    }
    
    public EmpleadoHorarios(EmpleadoHorariosPK id, PersonaEmpleado empleado, Horarios horario) {
        this.id = id;
        this.empleado = empleado;
        this.horario = horario;
    }
    
    public EmpleadoHorarios(PersonaEmpleado empleado, Horarios horario) {
        this.empleado = empleado;
        this.horario = horario;
        this.id = new EmpleadoHorariosPK(empleado.getIdPerEmpleado(), horario.getIdHorario());
    }
    
    // Getters y Setters
    public EmpleadoHorariosPK getId() {
        return id;
    }
    
    public void setId(EmpleadoHorariosPK id) {
        this.id = id;
    }
    
    public PersonaEmpleado getEmpleado() {
        return empleado;
    }
    
    public void setEmpleado(PersonaEmpleado empleado) {
        this.empleado = empleado;
        if (this.id == null) {
            this.id = new EmpleadoHorariosPK();
        }
        this.id.setIdPerEmpleado(empleado.getIdPerEmpleado());
    }
    
    public Horarios getHorario() {
        return horario;
    }
    
    public void setHorario(Horarios horario) {
        this.horario = horario;
        if (this.id == null) {
            this.id = new EmpleadoHorariosPK();
        }
        this.id.setIdHorario(horario.getIdHorario());
    }
}