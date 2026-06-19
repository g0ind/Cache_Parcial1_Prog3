package ar.edu.unlar.ecoride.model;

public class EstadoEnViaje implements EstadoVehiculo {
    @Override
    public void iniciarViaje(Vehiculo vehiculo) {
        throw new IllegalStateException("El vehículo ya se encuentra en viaje.");
    }

    @Override
    public void finalizarViaje(Vehiculo vehiculo) {
        vehiculo.setEstado(new EstadoEnEspera());
    }

    @Override
    public void enviarAReparacion(Vehiculo vehiculo) {
        throw new IllegalStateException("No se puede enviar a reparación un vehículo que está en viaje.");
    }

    @Override
    public String getNombre() {
        return "En Viaje";
    }
}
