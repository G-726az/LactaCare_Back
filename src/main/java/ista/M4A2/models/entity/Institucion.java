package ista.M4A2.models.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Institucion")
public class Institucion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Institucion")
    private Long idInstitucion; // INTEGER

    @Column(name = "Nombre_Institucion")
    private String nombreInstitucion; // VARCHAR

    @Lob 
    @Column(name = "Logo_Institucion")
    private byte[] logoInstitucion; // LONGBOB (mapeado a byte[])

    // Uno a Muchos con sala_lactancua (fk_Lactario_Id_Institucion_Institucion)
    @OneToMany(mappedBy = "institucion", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Sala_Lactancia> salas_lactancia;

	public Long getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(Long idInstitucion) {
		this.idInstitucion = idInstitucion;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	public byte[] getLogoInstitucion() {
		return logoInstitucion;
	}

	public void setLogoInstitucion(byte[] logoInstitucion) {
		this.logoInstitucion = logoInstitucion;
	}

	public List<Sala_Lactancia> getSalas_lactancia() {
		return salas_lactancia;
	}

	public void setSalas_lactancia(List<Sala_Lactancia> salas_lactancia) {
		this.salas_lactancia = salas_lactancia;
	}

    
}

