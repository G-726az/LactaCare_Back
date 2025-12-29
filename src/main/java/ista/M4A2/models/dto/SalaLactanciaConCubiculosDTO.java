package ista.M4A2.models.dto;

import ista.M4A2.models.entity.Sala_Lactancia;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para crear una sala de lactancia con sus cubículos
 */
public class SalaLactanciaConCubiculosDTO {
    
    @NotNull(message = "La sala de lactancia es requerida")
    private Sala_Lactancia salaLactancia;
    
    @NotNull(message = "El número de cubículos es requerido")
    @Min(value = 1, message = "Debe haber al menos 1 cubículo")
    private Integer numeroCubiculos;

    // Constructores
    public SalaLactanciaConCubiculosDTO() {}

    public SalaLactanciaConCubiculosDTO(Sala_Lactancia salaLactancia, Integer numeroCubiculos) {
        this.salaLactancia = salaLactancia;
        this.numeroCubiculos = numeroCubiculos;
    }

    // Getters y Setters
    public Sala_Lactancia getSalaLactancia() {
        return salaLactancia;
    }

    public void setSalaLactancia(Sala_Lactancia salaLactancia) {
        this.salaLactancia = salaLactancia;
    }

    public Integer getNumeroCubiculos() {
        return numeroCubiculos;
    }

    public void setNumeroCubiculos(Integer numeroCubiculos) {
        this.numeroCubiculos = numeroCubiculos;
    }
}