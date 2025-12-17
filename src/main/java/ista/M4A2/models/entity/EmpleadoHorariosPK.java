package ista.M4A2.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmpleadoHorariosPK implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "Id_PerEmpleado")
    private Integer idPerEmpleado;
    
    @Column(name = "Id_Horario")
    private Integer idHorario;
    
    // Constructores
    public EmpleadoHorariosPK() {
    }
    
    public EmpleadoHorariosPK(Integer idPerEmpleado, Integer idHorario) {
        this.idPerEmpleado = idPerEmpleado;
        this.idHorario = idHorario;
    }
    
    // Getters y Setters
    public Integer getIdPerEmpleado() {
        return idPerEmpleado;
    }
    
    public void setIdPerEmpleado(Integer idPerEmpleado) {
        this.idPerEmpleado = idPerEmpleado;
    }
    
    public Integer getIdHorario() {
        return idHorario;
    }
    
    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }
    
    // equals y hashCode (necesarios para claves compuestas)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpleadoHorariosPK that = (EmpleadoHorariosPK) o;
        return Objects.equals(idPerEmpleado, that.idPerEmpleado) && 
               Objects.equals(idHorario, that.idHorario);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(idPerEmpleado, idHorario);
    }
}