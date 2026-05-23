package ar.edu.unlar.ecoride.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class UsuarioRegular extends Usuario {

    @Override
    public double calcularDescuento(double total) {
        return 0.0;
    }
}
