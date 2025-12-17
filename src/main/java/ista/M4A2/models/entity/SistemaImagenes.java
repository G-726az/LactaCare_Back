package ista.M4A2.models.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Sistema_Imagenes")
public class SistemaImagenes implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_imagenes")
    private Integer idImagenes;
    
    @Column(name = "Categoria_Imagenes")
    private String categoriaImagenes;
    
    @Column(name = "Link_imagens")
    private String linkImagenes;
    
    // Constructores
    public SistemaImagenes() {
    }
    
    public SistemaImagenes(Integer idImagenes, String categoriaImagenes, String linkImagenes) {
        this.idImagenes = idImagenes;
        this.categoriaImagenes = categoriaImagenes;
        this.linkImagenes = linkImagenes;
    }
    
    // Getters y Setters
    public Integer getIdImagenes() {
        return idImagenes;
    }
    
    public void setIdImagenes(Integer idImagenes) {
        this.idImagenes = idImagenes;
    }
    
    public String getCategoriaImagenes() {
        return categoriaImagenes;
    }
    
    public void setCategoriaImagenes(String categoriaImagenes) {
        this.categoriaImagenes = categoriaImagenes;
    }
    
    public String getLinkImagenes() {
        return linkImagenes;
    }
    
    public void setLinkImagenes(String linkImagenes) {
        this.linkImagenes = linkImagenes;
    }
}