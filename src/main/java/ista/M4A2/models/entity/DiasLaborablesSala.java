package ista.M4A2.models.entity;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Dias_Laborables_Sala")
public class DiasLaborablesSala implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dia_laborable_sala")
    private Long idDiaLaborableSala; 

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
    
    // Relación inversa con salas
    @OneToMany(mappedBy = "diasLaborablesSala", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("diasLaborablesSala")
    private List<Sala_Lactancia> salas;

    // Getters y Setters
    public Long getIdDiaLaborableSala() {
        return idDiaLaborableSala;
    }

    public void setIdDiaLaborableSala(Long idDiaLaborableSala) {
        this.idDiaLaborableSala = idDiaLaborableSala;
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

    public List<Sala_Lactancia> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala_Lactancia> salas) {
        this.salas = salas;
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
            return "Sala cerrada todos los días";
        }
        
        return String.join(", ", dias);
    }
}