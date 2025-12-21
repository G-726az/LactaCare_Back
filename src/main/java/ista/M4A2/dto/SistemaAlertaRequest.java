package ista.M4A2.dto;

import java.time.LocalDateTime;

public class SistemaAlertaRequest {
	private String tipo_alerta;
	private float temperatura_alerta;
	private LocalDateTime fecha_hora_alerta;
	
	public String getTipo_alerta() {
		return tipo_alerta;
	}
	
	public void setTipo_alerta(String tipo_alerta) {
		this.tipo_alerta = tipo_alerta;
	}
	
	public float getTemperatura_alerta() {
		return temperatura_alerta;
	}
	
	public void setTemperatura_alerta(float temperatura_alerta) {
		this.temperatura_alerta = temperatura_alerta;
	}
	
	public LocalDateTime getFecha_hora_alerta() {
		return fecha_hora_alerta;
	}
	
	public void setFecha_hora_alerta(LocalDateTime fecha_hora_alerta) {
		this.fecha_hora_alerta = fecha_hora_alerta;
	}
}
