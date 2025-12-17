package ista.M4A2.models.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "lactantes")
public class Lactante implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre", length = 100)
	private String nombre;
	
	@Column(name = "fecha_nacimiento")
	private LocalDate fechaNacimiento;
	
	@Column(name = "genero", length = 10)
	private String genero;
	
	//Union con la entidad PersonaPaciente, un Lactante viene de una PersonaPaciente
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public PersonaPaciente getPersonaPaciente() {
		return personaPaciente;
	}

	public void setPersonaPaciente(PersonaPaciente personaPaciente) {
		this.personaPaciente = personaPaciente;
	}

}
