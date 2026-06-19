package ar.edu.unlar.ecoride.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Vehiculo implements Comparable<Vehiculo> {
    private String patente;
    private int porcentajeBateria;
    private double tarifaFijaBase;

    @Builder.Default
    private EstadoVehiculo estado = new EstadoEnEspera();

    public void iniciarViaje() {
        if (this.estado == null) {
            this.estado = new EstadoEnEspera();
        }
        this.estado.iniciarViaje(this);
    }

    public void finalizarViaje() {
        if (this.estado == null) {
            this.estado = new EstadoEnEspera();
        }
        this.estado.finalizarViaje(this);
    }

    public void enviarAReparacion() {
        if (this.estado == null) {
            this.estado = new EstadoEnEspera();
        }
        this.estado.enviarAReparacion(this);
    }

    public String getNombreEstado() {
        return this.estado != null ? this.estado.getNombre() : "Desconocido";
    }

    @Override
    public int compareTo(Vehiculo o) {
        if (o == null) {
            return 1;
        }
        return Integer.compare(this.porcentajeBateria, o.porcentajeBateria);
    }
}
