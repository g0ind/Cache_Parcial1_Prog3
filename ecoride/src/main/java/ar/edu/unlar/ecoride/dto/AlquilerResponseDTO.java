package ar.edu.unlar.ecoride.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlquilerResponseDTO {
    private String patente;
    private double costoFinal;
    private int minutosTranscurridos;
    private String estadoVehiculo;
    private String mensaje;
}
