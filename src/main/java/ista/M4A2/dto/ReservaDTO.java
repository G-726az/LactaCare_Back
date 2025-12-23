package ista.M4A2.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaDTO {

    private Long idReserva;
    private String estado;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    
    // Información aplanada (sin objetos anidados que causen bucles)
    private Long idPaciente;
    private String nombrePaciente;
    private String apellidoPaciente;
    
    private Long idSala;
    private String nombreSala;
    private String nombreInstitucion;
    
    public ReservaDTO() {}

    // Getters y Setters
    public Long getIdReserva() { return idReserva; }
    public void setIdReserva(Long idReserva) { this.idReserva = idReserva; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }
    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }
    public String getApellidoPaciente() { return apellidoPaciente; }
    public void setApellidoPaciente(String apellidoPaciente) { this.apellidoPaciente = apellidoPaciente; }
    public Long getIdSala() { return idSala; }
    public void setIdSala(Long idSala) { this.idSala = idSala; }
    public String getNombreSala() { return nombreSala; }
    public void setNombreSala(String nombreSala) { this.nombreSala = nombreSala; }
    public String getNombreInstitucion() { return nombreInstitucion; }
    public void setNombreInstitucion(String nombreInstitucion) { this.nombreInstitucion = nombreInstitucion; }
}
