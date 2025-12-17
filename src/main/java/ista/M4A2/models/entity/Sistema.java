package ista.M4A2.models.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Sistema")
public class Sistema implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Sistema")
    private Integer idSistema;
    
    @Column(name = "Logo_Sistema")
    private String logoSistema;
    
    @Column(name = "Nombre_Sistema")
    private String nombreSistema;
    
    @Column(name = "Eslogan")
    private String eslogan;
    
    // Constructores
    public Sistema() {
    }
    
    public Sistema(Integer idSistema, String logoSistema, String nombreSistema, String eslogan) {
        this.idSistema = idSistema;
        this.logoSistema = logoSistema;
        this.nombreSistema = nombreSistema;
        this.eslogan = eslogan;
    }
    
    // Getters y Setters
    public Integer getIdSistema() {
        return idSistema;
    }
    
    public void setIdSistema(Integer idSistema) {
        this.idSistema = idSistema;
    }
    
    public String getLogoSistema() {
        return logoSistema;
    }
    
    public void setLogoSistema(String logoSistema) {
        this.logoSistema = logoSistema;
    }
    
    public String getNombreSistema() {
        return nombreSistema;
    }
    
    public void setNombreSistema(String nombreSistema) {
        this.nombreSistema = nombreSistema;
    }
    
    public String getEslogan() {
        return eslogan;
    }
    
    public void setEslogan(String eslogan) {
        this.eslogan = eslogan;
    }
}