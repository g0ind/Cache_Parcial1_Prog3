package ar.edu.unlar.ecoride.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Vehiculo {
    private String patente;
    private int porcentajeBateria;
    private double tarifaFijaBase;
}
