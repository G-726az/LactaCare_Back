package ista.M4A2.models.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "atenciones")
public class Atencion implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "fecha")
	private LocalDate fecha;
	
	@Column(name = "hora")
	private LocalTime hora;
	
	//Union con la entidad Reserva, una atencion viene de una reserva
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_reserva", referencedColumnName = "id")
	@JsonIgnoreProperties("atencion")
	private Reserva reserva;
	
	//Union con la entidad ContenedorLeche, una atencion puede tener varios contenedores de leche
	@OneToMany(mappedBy = "atencion")
	@JsonIgnoreProperties("atencion")
	private List<ContenedorLeche> contenedoresLeche;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_persona_empleado", referencedColumnName = "Id_PerEmpleado")
	@JsonIgnoreProperties({"atenciones", "horarios", "rol"})
	private PersonaEmpleado empleado;
	
	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	
	public void setHora(LocalTime hora) {
		this.hora = hora;
	}
	
	public LocalTime getHora() {
		return hora;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public List<ContenedorLeche> getContenedoresLeche() {
		return contenedoresLeche;
	}

	public void setContenedoresLeche(List<ContenedorLeche> contenedoresLeche) {
		this.contenedoresLeche = contenedoresLeche;
	}

}
