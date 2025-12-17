package ista.M4A2.models.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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
	
	//Union con la entidad Atencion, una Reserva tiene de una atencion
	@OneToOne(mappedBy = "reserva", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("reserva") 
	private Atencion atencion;
	
	//Union con la entidad PersonaPaciente, una Reserva tiene una PersonaPaciente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_persona_paciente", referencedColumnName = "id")
	@JsonIgnoreProperties({"reservas", "contactosEmergencia", "lactantes"}) 
	private PersonaPaciente personaPaciente;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}
	
	public LocalTime getHoraInicio() {
		return horaInicio;
	}
	
	public void setHoraFin(LocalTime horaFin) {
		this.horaFin = horaFin;
	}
	
	public LocalTime getHoraFin() {
		return horaFin;
	}

	public Atencion getAtencion() {
		return atencion;
	}

	public void setAtencion(Atencion atencion) {
		this.atencion = atencion;
	}

	public PersonaPaciente getPaciente() {
		return personaPaciente;
	}

	public void setPaciente(PersonaPaciente personaPaciente) {
		this.personaPaciente = personaPaciente;
	}
	
}
