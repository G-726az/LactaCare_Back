package ista.M4A2.models.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Sala_Lactancia")
public class Sala_Lactancia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Lactario")
    private Long idLactario;

    // Campos simples
    @Column(name = "Nombre_CMedico") private String nombreCMedico;
    @Column(name = "Direccion_CMedico") private String direccionCMedico;
    @Column(name = "Correo_CMedico") private String correoCMedico;
    @Column(name = "Telefono_CMedico") private String telefonoCMedico;
    @Column(name = "Latitud_CMedico") private String latitudCMedico;
    @Column(name = "Longitud_CMedico") private String longitudCMedico;

    // Relaciones (Vuelven a ser LAZY y limpias, como debe ser)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Horario_CMedico", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private Horarios horario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Institucion")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private Institucion institucion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_diaslaborables")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private Dias_Laborables diasLaborables;
    
    @OneToMany(mappedBy = "sala_lactancia", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("sala_lactancia") 
    private List<Refrigerador> refrigeradores;

    // --- GETTERS Y SETTERS ---

    public Long getIdLactario() {
        return idLactario;
    }

    public void setIdLactario(Long idLactario) {
        this.idLactario = idLactario;
    }

    public String getNombreCMedico() {
        return nombreCMedico;
    }

    public void setNombreCMedico(String nombreCMedico) {
        this.nombreCMedico = nombreCMedico;
    }

    public String getDireccionCMedico() {
        return direccionCMedico;
    }

    public void setDireccionCMedico(String direccionCMedico) {
        this.direccionCMedico = direccionCMedico;
    }

    public String getCorreoCMedico() {
        return correoCMedico;
    }

    public void setCorreoCMedico(String correoCMedico) {
        this.correoCMedico = correoCMedico;
    }

    public String getTelefonoCMedico() {
        return telefonoCMedico;
    }

    public void setTelefonoCMedico(String telefonoCMedico) {
        this.telefonoCMedico = telefonoCMedico;
    }

    public String getLatitudCMedico() {
        return latitudCMedico;
    }

    public void setLatitudCMedico(String latitudCMedico) {
        this.latitudCMedico = latitudCMedico;
    }

    public String getLongitudCMedico() {
        return longitudCMedico;
    }

    public void setLongitudCMedico(String longitudCMedico) {
        this.longitudCMedico = longitudCMedico;
    }

    public Horarios getHorario() {
        return horario;
    }

    public void setHorario(Horarios horario) {
        this.horario = horario;
    }

    public Institucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

    public Dias_Laborables getDiasLaborables() {
        return diasLaborables;
    }

    public void setDiasLaborables(Dias_Laborables diasLaborables) {
        this.diasLaborables = diasLaborables;
    }

    public List<Refrigerador> getRefrigeradores() {
        return refrigeradores;
    }

    public void setRefrigeradores(List<Refrigerador> refrigeradores) {
        this.refrigeradores = refrigeradores;
    }
    
    // --- TU MÉTODO RESTAURADO ---
    public String aTexto() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("- Nombre Centro: ").append(nombreCMedico != null ? nombreCMedico : "Sin nombre");
        sb.append(", Dirección: ").append(direccionCMedico != null ? direccionCMedico : "Sin dirección");
        sb.append(", Teléfono: ").append(telefonoCMedico != null ? telefonoCMedico : "No registrado");
        
        if (institucion != null) {
            sb.append(", Institución: ").append(institucion.getNombreInstitucion()); 
        }

        if (horario != null) {
            sb.append(", Horario: ").append(horario.obtenerTextoHorario());
        } else {
            sb.append(", Horario: No definido");
        }

        if (diasLaborables != null) {
            sb.append(", Días de Atención: ").append(diasLaborables.obtenerDiasTexto());
        } else {
            sb.append(", Días de Atención: No especificado");
        }
        
        if (refrigeradores != null && !refrigeradores.isEmpty()) {
            sb.append(", Refrigeradores disponibles: ");
            for (Refrigerador ref : refrigeradores) {
                sb.append("[").append(ref.aTexto()).append("] ");
            }
        } else {
            sb.append(", Refrigeradores: No registrados");
        }
        
        return sb.toString();
    }
}