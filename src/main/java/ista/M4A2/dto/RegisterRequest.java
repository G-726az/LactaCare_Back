package ista.M4A2.dto;

public class RegisterRequest {
    private String cedula;
    private String primer_nombre;
    private String segundo_nombre;
    private String primer_apellido;
    private String segundo_apellido;
    private String correo;
    private String telefono;
    private String fecha_nacimiento;
    private String password;
    private String tipo_usuario; // "paciente" o "empleado"
    private String rol_empleado; // Solo para empleados
    private boolean discapacidad; // Solo para pacientes

    // Constructores
    public RegisterRequest() {}

    // Getters y Setters
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getPrimer_nombre() { return primer_nombre; }
    public void setPrimer_nombre(String primer_nombre) { this.primer_nombre = primer_nombre; }

    public String getSegundo_nombre() { return segundo_nombre; }
    public void setSegundo_nombre(String segundo_nombre) { this.segundo_nombre = segundo_nombre; }

    public String getPrimer_apellido() { return primer_apellido; }
    public void setPrimer_apellido(String primer_apellido) { this.primer_apellido = primer_apellido; }

    public String getSegundo_apellido() { return segundo_apellido; }
    public void setSegundo_apellido(String segundo_apellido) { this.segundo_apellido = segundo_apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getFecha_nacimiento() { return fecha_nacimiento; }
    public void setFecha_nacimiento(String fecha_nacimiento) { this.fecha_nacimiento = fecha_nacimiento; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTipo_usuario() { return tipo_usuario; }
    public void setTipo_usuario(String tipo_usuario) { this.tipo_usuario = tipo_usuario; }

    public String getRol_empleado() { return rol_empleado; }
    public void setRol_empleado(String rol_empleado) { this.rol_empleado = rol_empleado; }

    public boolean isDiscapacidad() { return discapacidad; }
    public void setDiscapacidad(boolean discapacidad) { this.discapacidad = discapacidad; }
}