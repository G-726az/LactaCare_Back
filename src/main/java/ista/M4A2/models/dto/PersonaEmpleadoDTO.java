package ista.M4A2.models.dto;

import java.time.LocalDate;

public class PersonaEmpleadoDTO {
    private Integer idPerEmpleado;
    private String perfilEmpleadoImg;
    private String cedula;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String correo;
    private String telefono;
    private LocalDate fechaNacimiento;
    
    // Solo información básica del rol (sin la colección de empleados)
    private RolDTO rol;
    
    // Información de relaciones si es necesario
    private Integer salaLactanciaId;
    private Integer horarioEmpleadoId;
    
    // Constructores
    public PersonaEmpleadoDTO() {
    }
    
    // Getters y Setters
    public Integer getIdPerEmpleado() {
        return idPerEmpleado;
    }

    public void setIdPerEmpleado(Integer idPerEmpleado) {
        this.idPerEmpleado = idPerEmpleado;
    }

    public String getPerfilEmpleadoImg() {
        return perfilEmpleadoImg;
    }

    public void setPerfilEmpleadoImg(String perfilEmpleadoImg) {
        this.perfilEmpleadoImg = perfilEmpleadoImg;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public RolDTO getRol() {
        return rol;
    }

    public void setRol(RolDTO rol) {
        this.rol = rol;
    }

    public Integer getSalaLactanciaId() {
        return salaLactanciaId;
    }

    public void setSalaLactanciaId(Integer salaLactanciaId) {
        this.salaLactanciaId = salaLactanciaId;
    }

    public Integer getHorarioEmpleadoId() {
        return horarioEmpleadoId;
    }

    public void setHorarioEmpleadoId(Integer horarioEmpleadoId) {
        this.horarioEmpleadoId = horarioEmpleadoId;
    }

    // Clase interna para RolDTO
    public static class RolDTO {
        private Integer idRol;
        private String nombreRol;
        
        public RolDTO() {
        }
        
        public RolDTO(Integer idRol, String nombreRol) {
            this.idRol = idRol;
            this.nombreRol = nombreRol;
        }

        public Integer getIdRol() {
            return idRol;
        }

        public void setIdRol(Integer idRol) {
            this.idRol = idRol;
        }

        public String getNombreRol() {
            return nombreRol;
        }

        public void setNombreRol(String nombreRol) {
            this.nombreRol = nombreRol;
        }
    }
}