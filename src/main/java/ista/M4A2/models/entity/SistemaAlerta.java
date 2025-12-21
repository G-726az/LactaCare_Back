package ista.M4A2.models.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sistema_alertas")
public class SistemaAlerta implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "tipo_alerta", length = 10)
	private String tipoAlerta;
	
	@Column(name = "temperatura_alerta", length = 5)
	private float temperaturaAlerta;
	
	@Column(name = "fecha_hora_alerta")
	private LocalDateTime fechaHoraAlerta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoAlerta() {
		return tipoAlerta;
	}

	public void setTipoAlerta(String tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}

	public float getTemperaturaAlerta() {
		return temperaturaAlerta;
	}
	
	public void setTemperaturaAlerta(float temperaturaAlerta) {
		this.temperaturaAlerta = temperaturaAlerta;
	}
	
	public LocalDateTime getFechaHoraAlerta() {
		return fechaHoraAlerta;
	}
	
	public void setFechaHoraAlerta(LocalDateTime fechaHoraAlerta) {
		this.fechaHoraAlerta = fechaHoraAlerta;
	}
	
}
