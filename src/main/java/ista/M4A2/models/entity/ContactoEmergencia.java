package ista.M4A2.models.entity;

import java.io.Serializable;

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
@Table(name = "contactos_emergencia")
public class ContactoEmergencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombre", length = 100)
	private String nombre;
	
	@Column(name = "telefono", length = 15)
	private String telefono;
	
	//Union con la entidad PersonaPaciente, un ContactoEmergencia viene de una PersonaPaciente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_persona_paciente", referencedColumnName = "id")
	@JsonIgnoreProperties({"reservas", "contactosEmergencia", "lactantes"}) 
	private PersonaPaciente personaPaciente;

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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public PersonaPaciente getPersonaPaciente() {
		return personaPaciente;
	}

	public void setPersonaPaciente(PersonaPaciente personaPaciente) {
		this.personaPaciente = personaPaciente;
	}

}
