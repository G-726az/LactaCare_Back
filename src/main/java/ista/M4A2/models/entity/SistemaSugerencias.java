package ista.M4A2.models.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Sistema_Sugerencias")
public class SistemaSugerencias implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Sugerencias")
    private Integer idSugerencias;
    
    @Column(name = "Titulo_Sugerencias")
    private String tituloSugerencias;
    
    @Column(name = "Detalle Sugerencias")
    private String detalleSugerencias;
    
    @Column(name = "Link_Imagen")
    private String linkImagen;
    
    // Constructores
    public SistemaSugerencias() {
    }
    
    public SistemaSugerencias(Integer idSugerencias, String tituloSugerencias, 
                              String detalleSugerencias, String linkImagen) {
        this.idSugerencias = idSugerencias;
        this.tituloSugerencias = tituloSugerencias;
        this.detalleSugerencias = detalleSugerencias;
        this.linkImagen = linkImagen;
    }
    
    // Getters y Setters
    public Integer getIdSugerencias() {
        return idSugerencias;
    }
    
    public void setIdSugerencias(Integer idSugerencias) {
        this.idSugerencias = idSugerencias;
    }
    
    public String getTituloSugerencias() {
        return tituloSugerencias;
    }
    
    public void setTituloSugerencias(String tituloSugerencias) {
        this.tituloSugerencias = tituloSugerencias;
    }
    
    public String getDetalleSugerencias() {
        return detalleSugerencias;
    }
    
    public void setDetalleSugerencias(String detalleSugerencias) {
        this.detalleSugerencias = detalleSugerencias;
    }
    
    public String getLinkImagen() {
        return linkImagen;
    }
    
    public void setLinkImagen(String linkImagen) {
        this.linkImagen = linkImagen;
    }
}