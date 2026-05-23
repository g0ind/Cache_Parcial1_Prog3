package ar.edu.unlar.ecoride.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Usuario {
    private String id;
    private String nombreCompleto;

    public abstract double calcularDescuento(double total);
}
