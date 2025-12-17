package ista.M4A2.models.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "Horarios")
public class Horarios implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Horario")
    private Integer idHorario;
    
    @Column(name = "Hora_Inicio_Jornada")
    private LocalTime horaInicioJornada;
    
    @Column(name = "Hora_Fin_Jornada")
    private LocalTime horaFinJornada;
    
    @Column(name = "Hora_Inicio_Descanso")
    private LocalTime horaInicioDescanso;
    
    @Column(name = "Hora_Fin_Descanso")
    private LocalTime horaFinDescanso;
    
    @OneToMany(mappedBy = "horario")
    private List<Sala_Lactancia> lactarios;
    
    @ManyToMany(mappedBy = "horarios")
    private List<PersonaEmpleado> empleados;
    
    // Constructores
    public Horarios() {
    }
    
    public Horarios(Integer idHorario, LocalTime horaInicioJornada, LocalTime horaFinJornada, 
                    LocalTime horaInicioDescanso, LocalTime horaFinDescanso) {
        this.idHorario = idHorario;
        this.horaInicioJornada = horaInicioJornada;
        this.horaFinJornada = horaFinJornada;
        this.horaInicioDescanso = horaInicioDescanso;
        this.horaFinDescanso = horaFinDescanso;
    }
    
    // Getters y Setters
    public Integer getIdHorario() {
        return idHorario;
    }
    
    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }
    
    public LocalTime getHoraInicioJornada() {
        return horaInicioJornada;
    }
    
    public void setHoraInicioJornada(LocalTime horaInicioJornada) {
        this.horaInicioJornada = horaInicioJornada;
    }
    
    public LocalTime getHoraFinJornada() {
        return horaFinJornada;
    }
    
    public void setHoraFinJornada(LocalTime horaFinJornada) {
        this.horaFinJornada = horaFinJornada;
    }
    
    public LocalTime getHoraInicioDescanso() {
        return horaInicioDescanso;
    }
    
    public void setHoraInicioDescanso(LocalTime horaInicioDescanso) {
        this.horaInicioDescanso = horaInicioDescanso;
    }
    
    public LocalTime getHoraFinDescanso() {
        return horaFinDescanso;
    }
    
    public void setHoraFinDescanso(LocalTime horaFinDescanso) {
        this.horaFinDescanso = horaFinDescanso;
    }
    
    public List<Sala_Lactancia> getLactarios() {
        return lactarios;
    }
    
    public void setLactarios(List<Sala_Lactancia> lactarios) {
        this.lactarios = lactarios;
    }
    
    public List<PersonaEmpleado> getEmpleados() {
        return empleados;
    }
    
    public void setEmpleados(List<PersonaEmpleado> empleados) {
        this.empleados = empleados;
    }
    public String obtenerTextoHorario() {
        StringBuilder sb = new StringBuilder();
        
        
        if (horaInicioJornada != null && horaFinJornada != null) {
            sb.append(horaInicioJornada).append(" a ").append(horaFinJornada);
        } else {
            sb.append("Horario no definido");
        }

        
        if (horaInicioDescanso != null && horaFinDescanso != null) {
            sb.append(" (Descanso/Almuerzo: ")
              .append(horaInicioDescanso).append(" a ").append(horaFinDescanso)
              .append(")");
        }
        
        return sb.toString();
    }
}