package ar.edu.unlar.ecoride.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UsuarioPremium extends Usuario {

    @Builder.Default
    private double descuentoFijo = 0.15;

    @Override
    public double calcularDescuento(double total) {
        return total * descuentoFijo;
    }
}
