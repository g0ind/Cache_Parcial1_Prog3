package ar.edu.unlar.ecoride.model;

public interface EstadoVehiculo {
    void iniciarViaje(Vehiculo vehiculo);
    void finalizarViaje(Vehiculo vehiculo);
    void enviarAReparacion(Vehiculo vehiculo);
    String getNombre();
}
