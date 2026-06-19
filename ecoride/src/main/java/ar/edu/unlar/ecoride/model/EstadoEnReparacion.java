package ar.edu.unlar.ecoride.model;

public class EstadoEnReparacion implements EstadoVehiculo {
    @Override
    public void iniciarViaje(Vehiculo vehiculo) {
        throw new IllegalStateException("No se puede iniciar un viaje en un vehículo que está en reparación.");
    }

    @Override
    public void finalizarViaje(Vehiculo vehiculo) {
        throw new IllegalStateException("No se puede finalizar viaje en un vehículo que está en reparación.");
    }

    @Override
    public void enviarAReparacion(Vehiculo vehiculo) {
        throw new IllegalStateException("El vehículo ya se encuentra en reparación.");
    }

    @Override
    public String getNombre() {
        return "En Reparación";
    }
}
