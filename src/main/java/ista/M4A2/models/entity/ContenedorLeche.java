package ista.M4A2.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name = "contenedores_leche")
public class ContenedorLeche implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "fecha_hora_extraccion")
	private LocalDateTime fechaHoraExtraccion;
	
	@Column(name = "fecha_hora_caducidad")
	private LocalDateTime fechaHoraCaducidad;
	
	@Column(name = "estado", length = 60)
	private String estado;
	
	@Column(name = "cantidad_mililitros", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
	private BigDecimal cantidadMililitros;
	
	//Union con la entidad Atencion, un ContenedorLeche viene de una Atencion
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_atencion", referencedColumnName = "id")
	@JsonIgnoreProperties({"contenedoresLeche", "reserva"})
	private Atencion atencion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFechaHoraExtraccion() {
		return fechaHoraExtraccion;
	}

	public void setFechaHoraExtraccion(LocalDateTime fechaHoraExtraccion) {
		this.fechaHoraExtraccion = fechaHoraExtraccion;
	}

	public LocalDateTime getFechaHoraCaducidad() {
		return fechaHoraCaducidad;
	}

	public void setFechaHoraCaducidad(LocalDateTime fechaHoraCaducidad) {
		this.fechaHoraCaducidad = fechaHoraCaducidad;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BigDecimal getCantidadMililitros() {
		return cantidadMililitros;
	}

	public void setCantidadMililitros(BigDecimal cantidadMililitros) {
		this.cantidadMililitros = cantidadMililitros;
	}

	public Atencion getAtencion() {
		return atencion;
	}

	public void setAtencion(Atencion atencion) {
		this.atencion = atencion;
	}

}
