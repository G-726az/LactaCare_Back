package ista.M4A2.models.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Dias_laborables")
public class Dias_Laborables implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dia_laborable")
    private Long idDiaLaborable; 

    @Column(name = "Dia_Lunes")
    private Boolean diaLunes; 

    @Column(name = "Dia_Martes")
    private Boolean diaMartes;
    
    @Column(name = "Dia_Miercoles")
    private Boolean diaMiercoles;
    
    @Column(name = "Dia_Jueves")
    private Boolean diaJueves;
    
    @Column(name = "Dia_Viernes")
    private Boolean diaViernes;
    
    @Column(name = "Dia_Sabado")
    private Boolean diaSabado;
    
    @Column(name = "Dia_Domingo")
    private Boolean diaDomingo;
    

    // Uno a Muchos con Sala_Lactancia (fk_Dias_laborables_id_dia_laborable_Lactario)
    @OneToMany(mappedBy = "diasLaborables")
    private List<Sala_Lactancia> lactarios;


	public Long getIdDiaLaborable() {
		return idDiaLaborable;
	}


	public void setIdDiaLaborable(Long idDiaLaborable) {
		this.idDiaLaborable = idDiaLaborable;
	}


	public Boolean getDiaLunes() {
		return diaLunes;
	}


	public void setDiaLunes(Boolean diaLunes) {
		this.diaLunes = diaLunes;
	}


	public Boolean getDiaMartes() {
		return diaMartes;
	}


	public void setDiaMartes(Boolean diaMartes) {
		this.diaMartes = diaMartes;
	}


	public Boolean getDiaMiercoles() {
		return diaMiercoles;
	}


	public void setDiaMiercoles(Boolean diaMiercoles) {
		this.diaMiercoles = diaMiercoles;
	}


	public Boolean getDiaJueves() {
		return diaJueves;
	}


	public void setDiaJueves(Boolean diaJueves) {
		this.diaJueves = diaJueves;
	}


	public Boolean getDiaViernes() {
		return diaViernes;
	}


	public void setDiaViernes(Boolean diaViernes) {
		this.diaViernes = diaViernes;
	}


	public Boolean getDiaSabado() {
		return diaSabado;
	}


	public void setDiaSabado(Boolean diaSabado) {
		this.diaSabado = diaSabado;
	}


	public Boolean getDiaDomingo() {
		return diaDomingo;
	}


	public void setDiaDomingo(Boolean diaDomingo) {
		this.diaDomingo = diaDomingo;
	}


	public List<Sala_Lactancia> getLactarios() {
		return lactarios;
	}


	public void setLactarios(List<Sala_Lactancia> lactarios) {
		this.lactarios = lactarios;
	}
	
	public String obtenerDiasTexto() {
        java.util.List<String> dias = new java.util.ArrayList<>();
        
        if (Boolean.TRUE.equals(this.diaLunes)) dias.add("Lunes");
        if (Boolean.TRUE.equals(this.diaMartes)) dias.add("Martes");
        if (Boolean.TRUE.equals(this.diaMiercoles)) dias.add("Miércoles");
        if (Boolean.TRUE.equals(this.diaJueves)) dias.add("Jueves");
        if (Boolean.TRUE.equals(this.diaViernes)) dias.add("Viernes");
        if (Boolean.TRUE.equals(this.diaSabado)) dias.add("Sábado");
        if (Boolean.TRUE.equals(this.diaDomingo)) dias.add("Domingo");

        if (dias.isEmpty()) {
            return "No hay días específicos registrados";
        }
        
        return String.join(", ", dias);
    }

}
