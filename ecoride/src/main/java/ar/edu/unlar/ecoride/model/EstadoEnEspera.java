package ar.edu.unlar.ecoride.model;

public class EstadoEnEspera implements EstadoVehiculo {
    @Override
    public void iniciarViaje(Vehiculo vehiculo) {
        vehiculo.setEstado(new EstadoEnViaje());
    }

    @Override
    public void finalizarViaje(Vehiculo vehiculo) {
        throw new IllegalStateException("No se puede finalizar viaje en un vehículo que está en espera.");
    }

    @Override
    public void enviarAReparacion(Vehiculo vehiculo) {
        vehiculo.setEstado(new EstadoEnReparacion());
    }

    @Override
    public String getNombre() {
        return "En Espera";
    }
}
