package ista.M4A2.models.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "personas_pacientes")
public class PersonaPaciente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "cedula", unique = true, length = 13)
	private String cedula;
	
	@Column(name = "imagen_perfil")
	private String imagenPerfil;
	
	@Column(name = "password", length = 100)
	private String password;
	
	@Column(name = "primer_nombre", length = 50)
	private String primerNombre;
	
	@Column(name = "segundo_nombre", length = 50)
	private String segundoNombre;
	
	@Column(name = "primer_apellido", length = 50)
	private String primerApellido;
	
	@Column(name = "segundo_apellido", length = 50)
	private String segundoApellido;
	
	@Column(name = "correo", unique = true, length = 100)
	private String correo;
	
	@Column(name = "telefono", length = 15)
	private String telefono;
	
	@Column(name = "fecha_nacimiento")
	private LocalDate fechaNacimiento;
	
	@Column(name = "discapacidad", columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean discapacidad;
	
	private String resetToken;
	
	private LocalDateTime tokenExpiration;
	
	//Union con la entidad Reservas, una PersonaPaciente puede tener varias Reservas
	@OneToMany(mappedBy = "personaPaciente")
	@JsonIgnoreProperties("personaPaciente")
	private List<Reserva> reservas;
	
	//Union con la entidad ContactoEmergencia, una PersonaPaciente puede tener varios Contactos de Emergencia
	@OneToMany(mappedBy = "personaPaciente", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("personaPaciente")
	private List<ContactoEmergencia> contactosEmergencia;
	
	//Union con la entidad Lactante, una PersonaPaciente puede tener varios Lactantes
	@OneToMany(mappedBy = "personaPaciente", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("personaPaciente")
	private List<Lactante> lactantes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	
	public String getImagenPerfil() {
		return imagenPerfil;
	}

	public void setImagenPerfil(String imagenPerfil) {
		this.imagenPerfil = imagenPerfil;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrimerNombre() {
		return primerNombre;
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoNombre() {
		return segundoNombre;
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public boolean isDiscapacidad() {
		return discapacidad;
	}
	
	public void setDiscapacidad(boolean discapacidad) {
		this.discapacidad = discapacidad;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	public List<ContactoEmergencia> getContactosEmergencia() {
		return contactosEmergencia;
	}

	public void setContactosEmergencia(List<ContactoEmergencia> contactosEmergencia) {
		this.contactosEmergencia = contactosEmergencia;
	}

	public List<Lactante> getLactantes() {
		return lactantes;
	}

	public void setLactantes(List<Lactante> lactantes) {
		this.lactantes = lactantes;
	}
	
	public String getResetToken() {
		return resetToken;
	}
	
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	
	public LocalDateTime getTokenExpiration() {
		return tokenExpiration;
	}
	
	public void setTokenExpiration(LocalDateTime tokenExpiration) {
		this.tokenExpiration = tokenExpiration;
	}
}
