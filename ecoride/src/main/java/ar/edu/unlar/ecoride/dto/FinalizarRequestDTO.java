package ar.edu.unlar.ecoride.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalizarRequestDTO {
    private String patente;
    private int minutosTranscurridos;
    private String metodoPago;
    private String idUsuario;
}
