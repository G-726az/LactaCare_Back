package ista.M4A2.dto;


public class PreguntaRequest {
    
    private String pregunta;
    private Double latitud;  
    private Double longitud; 

    
    public PreguntaRequest() {}

   
    public PreguntaRequest(String pregunta, Double latitud, Double longitud) {
        this.pregunta = pregunta;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Getters y Setters
    public String getPregunta() { return pregunta; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }
}