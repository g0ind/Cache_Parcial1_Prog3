package ar.edu.unlar.ecoride.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculoResponseDTO {
    private String patente;
    private int porcentajeBateria;
    private double tarifaFijaBase;
    private String estado;
}
