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

<<<<<<< HEAD
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
    
=======
    // === RELACIONES ===
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Institucion")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private Institucion institucion;
    
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
    @OneToMany(mappedBy = "sala_lactancia", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("sala_lactancia") 
    private List<Refrigerador> refrigeradores;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_horario_sala", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private HorariosSala horarioSala;

<<<<<<< HEAD
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
=======
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dia_laborable_sala")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private DiasLaborablesSala diasLaborablesSala;
    
    @OneToMany(mappedBy = "salaLactancia", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("salaLactancia")
    private List<PersonaEmpleado> empleados;

    @OneToMany(mappedBy = "salaLactancia", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("salaLactancia")
    private List<PersonaPaciente> pacientes;

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
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
    }

    public Institucion getInstitucion() {
        return institucion;
    }

    public void setInstitucion(Institucion institucion) {
        this.institucion = institucion;
    }

<<<<<<< HEAD
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
=======
    public List<Refrigerador> getRefrigeradores() {
        return refrigeradores;
    }

    public void setRefrigeradores(List<Refrigerador> refrigeradores) {
        this.refrigeradores = refrigeradores;
    }
    
    public List<PersonaEmpleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<PersonaEmpleado> empleados) {
        this.empleados = empleados;
    }

    public List<PersonaPaciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<PersonaPaciente> pacientes) {
        this.pacientes = pacientes;
    }
    
    public HorariosSala getHorarioSala() {
        return horarioSala;
    }

    public void setHorarioSala(HorariosSala horarioSala) {
        this.horarioSala = horarioSala;
    }

    public DiasLaborablesSala getDiasLaborablesSala() {
        return diasLaborablesSala;
    }

    public void setDiasLaborablesSala(DiasLaborablesSala diasLaborablesSala) {
        this.diasLaborablesSala = diasLaborablesSala;
    }
    
    
    
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
    public String aTexto() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("- Nombre Centro: ").append(nombreCMedico != null ? nombreCMedico : "Sin nombre");
        sb.append(", Dirección: ").append(direccionCMedico != null ? direccionCMedico : "Sin dirección");
        sb.append(", Teléfono: ").append(telefonoCMedico != null ? telefonoCMedico : "No registrado");
        
        if (institucion != null) {
            sb.append(", Institución: ").append(institucion.getNombreInstitucion()); 
        }

<<<<<<< HEAD
        if (horario != null) {
            sb.append(", Horario: ").append(horario.obtenerTextoHorario());
=======
        if (horarioSala != null) {
            sb.append(", Horario: ").append(horarioSala.obtenerTextoHorario());
>>>>>>> ddd2387 (relacion entre empleados y pacientes con sala_lactancia: separacion de horaio y dias laborables para empleados y sala_Lactancia; Modificacion de authcontroller y login R-R para inicio con correo (Solicitado), pendiente arreglo de service e imnplements 251225)
        } else {
            sb.append(", Horario: No definido");
        }

        if (diasLaborablesSala != null) {
            sb.append(", Días de Atención: ").append(diasLaborablesSala.obtenerDiasTexto());
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