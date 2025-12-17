package ista.M4A2.dto;

public class ValidationRequest {
    private String cedula;
    private String ruc;
    private String email;

    // Constructores
    public ValidationRequest() {}

    public ValidationRequest(String cedula, String ruc, String email) {
        this.cedula = cedula;
        this.ruc = ruc;
        this.email = email;
    }

    // Getters y Setters
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

// DTO para la respuesta de validación
class ValidationResponse {
    private boolean valid;
    private String message;
    private String type; // "cedula", "ruc", o "email"

    public ValidationResponse() {}

    public ValidationResponse(boolean valid, String message, String type) {
        this.valid = valid;
        this.message = message;
        this.type = type;
    }

    // Getters y Setters
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}