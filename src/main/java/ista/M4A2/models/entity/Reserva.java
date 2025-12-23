package ista.M4A2.models.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "reservas")
public class Reserva implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "estado", length = 20)
    private String estado;
    
    @Column(name = "fecha")
    private LocalDate fecha;
    
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;
    
    @Column(name = "hora_fin")
    private LocalTime horaFin;
    
    @OneToOne(mappedBy = "reserva", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("reserva") 
    private Atencion atencion;
    
    // VOLVEMOS A LAZY (Lo ideal)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona_paciente", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private PersonaPaciente personaPaciente;

    // VOLVEMOS A LAZY
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala_lactancia") 
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private Sala_Lactancia salaLactancia;

    // Getters y Setters (sin cambios, usa los que ya tenías)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public Atencion getAtencion() { return atencion; }
    public void setAtencion(Atencion atencion) { this.atencion = atencion; }
    public PersonaPaciente getPersonaPaciente() { return personaPaciente; }
    public void setPersonaPaciente(PersonaPaciente personaPaciente) { this.personaPaciente = personaPaciente; }
    public Sala_Lactancia getSalaLactancia() { return salaLactancia; }
    public void setSalaLactancia(Sala_Lactancia salaLactancia) { this.salaLactancia = salaLactancia; }
}