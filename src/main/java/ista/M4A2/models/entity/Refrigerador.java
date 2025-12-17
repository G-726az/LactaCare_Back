package ista.M4A2.models.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Refrigerador")
public class Refrigerador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_refrigerador")
    private Long idRefrigerador; // INTEGER

    // Relación Muchos a Uno con Lactario (fk_Refrigerador_Id_Lactario_Lactario)
    @ManyToOne
    @JoinColumn(name = "Id_Lactario")
    private Sala_Lactancia sala_lactancia; 
    
    @Column(name = "Capacidad_max_refri")
    private Integer capacidadMaxRefri; 
    
    @Column(name = "Piso_refrigerador")
    private Integer pisoRefrigerador;
    
    @Column(name = "Fila_regrigerador")
    private Integer filaRefrigerador;
    
    @Column(name = "Columna_refrigerador")
    private Integer columnaRefrigerador;
    
    // Relaciones Inversas (Estado_Refrigerador, Biberones, Ubicacion_Contenedor)
    @OneToMany(mappedBy = "refrigerador")
    private List<Estado_Refrigerador> estadosRefrigerador;
    
    @OneToMany(mappedBy = "refrigerador")
    private List<Ubicacion_Contenedor> ubicacionesContenedor;

	public Long getIdRefrigerador() {
		return idRefrigerador;
	}

	public void setIdRefrigerador(Long idRefrigerador) {
		this.idRefrigerador = idRefrigerador;
	}

	public Sala_Lactancia getSala_lactancia() {
		return sala_lactancia;
	}

	public void setSala_lactancia(Sala_Lactancia sala_lactancia) {
		this.sala_lactancia = sala_lactancia;
	}

	public Integer getCapacidadMaxRefri() {
		return capacidadMaxRefri;
	}

	public void setCapacidadMaxRefri(Integer capacidadMaxRefri) {
		this.capacidadMaxRefri = capacidadMaxRefri;
	}

	public Integer getPisoRefrigerador() {
		return pisoRefrigerador;
	}

	public void setPisoRefrigerador(Integer pisoRefrigerador) {
		this.pisoRefrigerador = pisoRefrigerador;
	}

	public Integer getFilaRefrigerador() {
		return filaRefrigerador;
	}

	public void setFilaRefrigerador(Integer filaRefrigerador) {
		this.filaRefrigerador = filaRefrigerador;
	}

	public Integer getColumnaRefrigerador() {
		return columnaRefrigerador;
	}

	public void setColumnaRefrigerador(Integer columnaRefrigerador) {
		this.columnaRefrigerador = columnaRefrigerador;
	}

	public List<Estado_Refrigerador> getEstadosRefrigerador() {
		return estadosRefrigerador;
	}

	public void setEstadosRefrigerador(List<Estado_Refrigerador> estadosRefrigerador) {
		this.estadosRefrigerador = estadosRefrigerador;
	}

	public List<Ubicacion_Contenedor> getUbicacionesContenedor() {
		return ubicacionesContenedor;
	}

	public void setUbicacionesContenedor(List<Ubicacion_Contenedor> ubicacionesContenedor) {
		this.ubicacionesContenedor = ubicacionesContenedor;
	}
	public String aTexto() {
        return "Refrigerador #" + idRefrigerador + 
               " (Ubicación: Piso " + pisoRefrigerador + 
               ", Fila " + filaRefrigerador + 
               ", Columna " + columnaRefrigerador + 
               ", Capacidad Máx: " + capacidadMaxRefri + ")";
    }

}
