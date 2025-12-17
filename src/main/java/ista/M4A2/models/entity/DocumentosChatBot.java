package ista.M4A2.models.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "documentos_chatbot")
public class DocumentosChatBot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private Long idDocumento;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;

    @Lob
    @Column(name = "contenido", columnDefinition = "LONGBLOB") 
    private byte[] contenido;

    @Column(name = "fecha_subida")
    private LocalDateTime fechaSubida;

    // --- Getters y Setters ---

    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }


    @PrePersist
    public void prePersist() {
        this.fechaSubida = LocalDateTime.now();
    }
    

    @Override
    public String toString() {
        return "DocumentosChatBot [id=" + idDocumento + ", nombre=" + nombreArchivo + ", tamaño=" + (contenido != null ? contenido.length : 0) + " bytes]";
    }
}