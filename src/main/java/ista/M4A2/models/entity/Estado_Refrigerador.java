package ista.M4A2.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "estado_refrigerador")
public class Estado_Refrigerador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_est_refrigerador")
    private Long idEstRefrigerador;


    @ManyToOne
    @JoinColumn(name = "id_refrigerador")
    private Refrigerador refrigerador; 


    @Column(name = "temperatura_est_refrigerador")
    private BigDecimal temperaturaEstRefrigerador;


    @Column(name = "humedad_est_refrigerador")
    private BigDecimal humedadEstRefrigerador;


    @Column(name = "hora_actividad_est_refrigerador")
    private LocalTime horaActividadEstRefrigerador;


    @Column(name = "fecha_actividad_est_refrigerador")
    @Temporal(TemporalType.DATE)
    private Date fechaActividadEstRefrigerador;

	public Long getIdEstRefrigerador() {
		return idEstRefrigerador;
	}

	public void setIdEstRefrigerador(Long idEstRefrigerador) {
		this.idEstRefrigerador = idEstRefrigerador;
	}

	public Refrigerador getRefrigerador() {
		return refrigerador;
	}

	public void setRefrigerador(Refrigerador refrigerador) {
		this.refrigerador = refrigerador;
	}

	public BigDecimal getTemperaturaEstRefrigerador() {
		return temperaturaEstRefrigerador;
	}

	public void setTemperaturaEstRefrigerador(BigDecimal temperaturaEstRefrigerador) {
		this.temperaturaEstRefrigerador = temperaturaEstRefrigerador;
	}

	public BigDecimal getHumedadEstRefrigerador() {
		return humedadEstRefrigerador;
	}

	public void setHumedadEstRefrigerador(BigDecimal humedadEstRefrigerador) {
		this.humedadEstRefrigerador = humedadEstRefrigerador;
	}

	public LocalTime getHoraActividadEstRefrigerador() {
		return horaActividadEstRefrigerador;
	}

	public void setHoraActividadEstRefrigerador(LocalTime horaActividadEstRefrigerador) {
		this.horaActividadEstRefrigerador = horaActividadEstRefrigerador;
	}

	public Date getFechaActividadEstRefrigerador() {
		return fechaActividadEstRefrigerador;
	}

	public void setFechaActividadEstRefrigerador(Date fechaActividadEstRefrigerador) {
		this.fechaActividadEstRefrigerador = fechaActividadEstRefrigerador;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
   
}
