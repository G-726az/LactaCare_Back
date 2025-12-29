package ista.M4A2.models.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Horarios_Sala")
public class HorariosSala implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Horario_Sala")
    private Integer idHorarioSala;
    
    @Column(name = "Hora_Apertura")
    private LocalTime horaApertura;
    
    @Column(name = "Hora_Cierre")
    private LocalTime horaCierre;
    
    @Column(name = "Hora_Inicio_Descanso")
    private LocalTime horaInicioDescanso;
    
    @Column(name = "Hora_Fin_Descanso")
    private LocalTime horaFinDescanso;
    
    // Relación inversa con salas
    @OneToMany(mappedBy = "horarioSala", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Sala_Lactancia> salas;
    
    // Constructores
    public HorariosSala() {
    }
    
    public HorariosSala(Integer idHorarioSala, LocalTime horaApertura, 
                       LocalTime horaCierre, LocalTime horaInicioDescanso, 
                       LocalTime horaFinDescanso) {
        this.idHorarioSala = idHorarioSala;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.horaInicioDescanso = horaInicioDescanso;
        this.horaFinDescanso = horaFinDescanso;
    }
    
    // Getters y Setters
    public Integer getIdHorarioSala() {
        return idHorarioSala;
    }
    
    public void setIdHorarioSala(Integer idHorarioSala) {
        this.idHorarioSala = idHorarioSala;
    }
    
    public LocalTime getHoraApertura() {
        return horaApertura;
    }
    
    public void setHoraApertura(LocalTime horaApertura) {
        this.horaApertura = horaApertura;
    }
    
    public LocalTime getHoraCierre() {
        return horaCierre;
    }
    
    public void setHoraCierre(LocalTime horaCierre) {
        this.horaCierre = horaCierre;
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
    
    public List<Sala_Lactancia> getSalas() {
        return salas;
    }
    
    public void setSalas(List<Sala_Lactancia> salas) {
        this.salas = salas;
    }
    
    public String obtenerTextoHorario() {
        StringBuilder sb = new StringBuilder();
        
        if (horaApertura != null && horaCierre != null) {
            sb.append(horaApertura).append(" a ").append(horaCierre);
        } else {
            sb.append("Horario no definido");
        }

        if (horaInicioDescanso != null && horaFinDescanso != null) {
            sb.append(" (Cerrado para descanso: ")
              .append(horaInicioDescanso).append(" a ").append(horaFinDescanso)
              .append(")");
        }
        
        return sb.toString();
    }
}