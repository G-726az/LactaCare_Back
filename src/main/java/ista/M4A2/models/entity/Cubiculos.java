package ista.M4A2.models.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "cubiculos")
public class Cubiculos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_cubiculo")
    private String nombreCb;

    @Column(name = "estado_cubiculo", length = 20)
    private String estadoCb;

    // Relación ManyToOne con Sala_Lactancia
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala_lactancia", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Sala_Lactancia salaLactancia;

    // Relación OneToMany con Reserva
    @OneToMany(mappedBy = "cubiculo", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("cubiculo")
    private List<Reserva> reservas;

    // Constructores
    public Cubiculos() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCb() {
        return nombreCb;
    }

    public void setNombreCb(String nombreCb) {
        this.nombreCb = nombreCb;
    }

    public String getEstadoCb() {
        return estadoCb;
    }

    public void setEstadoCb(String estadoCb) {
        this.estadoCb = estadoCb;
    }

    public Sala_Lactancia getSalaLactancia() {
        return salaLactancia;
    }

    public void setSalaLactancia(Sala_Lactancia salaLactancia) {
        this.salaLactancia = salaLactancia;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public String aTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cubículo ID: ").append(id);
        sb.append(", Nombre: ").append(nombreCb != null ? nombreCb : "Sin nombre");
        sb.append(", Estado: ").append(estadoCb != null ? estadoCb : "No definido");
        
        if (salaLactancia != null) {
            sb.append(", Sala: ").append(salaLactancia.getNombreCMedico());
        }
        
        return sb.toString();
    }
}