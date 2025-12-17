package ista.M4A2.models.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ubicacion_Contenedor")
public class Ubicacion_Contenedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubicacion")
    private Long idUbicacion; // INTEGER

    // One-to-One con Contenedor_Leche (id_contenedor)
    @OneToOne
    @JoinColumn(name = "id_contenedor")
    private ContenedorLeche contenedor; 

    //Muchos a Uno con Refrigerador (id refrigerador)
    @ManyToOne
    @JoinColumn(name = "id_refrigerador")
    private Refrigerador refrigerador; 
    
    @Column(name = "Piso_refrigerador")
    private Integer pisoRefrigerador;
    
    @Column(name = "Fila_refrigerador")
    private Integer filaRefrigerador;
    
    @Column(name = "Columna_refrigerador")
    private Integer columnaRefrigerador;

	public Long getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(Long idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public ContenedorLeche getContenedor() {
		return contenedor;
	}

	public void setContenedor(ContenedorLeche contenedor) {
		this.contenedor = contenedor;
	}

	public Refrigerador getRefrigerador() {
		return refrigerador;
	}

	public void setRefrigerador(Refrigerador refrigerador) {
		this.refrigerador = refrigerador;
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

    
}