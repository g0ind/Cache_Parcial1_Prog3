package ar.edu.unlar.ecoride.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlquilerRequestDTO {
    private String idUsuario;
    private String patente;
    private String metodoPago;
}
