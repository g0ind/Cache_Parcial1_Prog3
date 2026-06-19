package ar.edu.unlar.ecoride.service;

import ar.edu.unlar.ecoride.dto.AlquilerResponseDTO;
import ar.edu.unlar.ecoride.dto.VehiculoResponseDTO;
import ar.edu.unlar.ecoride.exception.BateriaInsuficienteException;
import ar.edu.unlar.ecoride.exception.VehiculoNoEncontradoException;
import ar.edu.unlar.ecoride.model.EstacionAnclaje;
import ar.edu.unlar.ecoride.model.Usuario;
import ar.edu.unlar.ecoride.model.Vehiculo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class AlquilerService {

    private final List<EstacionAnclaje> estaciones = new ArrayList<>();
    private final List<Usuario> usuarios = new ArrayList<>();
    private final PagoFactory pagoFactory;
    private final GestorTarifa gestorTarifa;

    public AlquilerService(PagoFactory pagoFactory, GestorTarifa gestorTarifa) {
        this.pagoFactory = pagoFactory;
        this.gestorTarifa = gestorTarifa;
    }

    public void agregarEstacion(EstacionAnclaje estacion) {
        this.estaciones.add(estacion);
    }

    public void agregarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public List<EstacionAnclaje> getEstaciones() {
        return estaciones;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public GestorTarifa getGestorTarifa() {
        return gestorTarifa;
    }

    public AlquilerResponseDTO procesarDesbloqueo(String idUsuario, String patente, String metodoPago) {
        // 1. Localizar el usuario
        Usuario usuario = null;
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            if (u.getId().equals(idUsuario)) {
                usuario = u;
                break;
            }
        }
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + idUsuario);
        }

        // 2. Localizar el vehículo
        Vehiculo vehiculo = null;
        for (int i = 0; i < estaciones.size(); i++) {
            EstacionAnclaje estacion = estaciones.get(i);
            Vehiculo encontrado = estacion.buscarVehiculoPorPatente(patente);
            if (encontrado != null) {
                vehiculo = encontrado;
                break;
            }
        }

        if (vehiculo == null) {
            throw new VehiculoNoEncontradoException("Vehículo No Encontrado");
        }

        // 3. Validar nivel de batería (mínimo 15%)
        if (vehiculo.getPorcentajeBateria() < 15) {
            throw new BateriaInsuficienteException("Batería Insuficiente");
        }

        // 4. Validar estado e iniciar viaje (Lanza IllegalStateException si no está En
        // Espera)
        vehiculo.iniciarViaje();

        // 5. Calcular el importe final considerando las características del usuario
        // (descuento)
        double totalBase = vehiculo.getTarifaFijaBase();
        double descuento = usuario.calcularDescuento(totalBase);
        double importeFinal = totalBase - descuento;

        // 6. Obtener el medio de pago adecuado y cobrar
        ProcesadorPago procesador = pagoFactory.crearProcesador(metodoPago);
        procesador.efectuarCobro(importeFinal);

        // 7. Retornar DTO de respuesta
        String tipoRodado = vehiculo.getClass().getSimpleName();
        String mensaje = String.format(Locale.US, "Desbloqueo exitoso. Rodado: %s (Patente: %s). Monto cobrado: $%.2f",
                tipoRodado, vehiculo.getPatente(), importeFinal);

        return AlquilerResponseDTO.builder()
                .patente(vehiculo.getPatente())
                .costoFinal(importeFinal)
                .minutosTranscurridos(0)
                .estadoVehiculo(vehiculo.getNombreEstado())
                .mensaje(mensaje)
                .build();
    }

    public AlquilerResponseDTO finalizarViaje(String patente, int minutosTranscurridos, String metodoPago,
            String idUsuario) {
        // 1. Localizar el usuario
        Usuario usuario = null;
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            if (u.getId().equals(idUsuario)) {
                usuario = u;
                break;
            }
        }
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + idUsuario);
        }

        // 2. Localizar el vehículo
        Vehiculo vehiculo = null;
        for (int i = 0; i < estaciones.size(); i++) {
            EstacionAnclaje estacion = estaciones.get(i);
            Vehiculo encontrado = estacion.buscarVehiculoPorPatente(patente);
            if (encontrado != null) {
                vehiculo = encontrado;
                break;
            }
        }

        if (vehiculo == null) {
            throw new VehiculoNoEncontradoException("Vehículo No Encontrado");
        }

        // 3. Finalizar viaje en el vehículo (Lanza IllegalStateException si no está En
        // Viaje)
        vehiculo.finalizarViaje();

        // 4. Calcular costo final usando la tarifa adaptativa
        double costoMinutos = gestorTarifa.calcular(vehiculo.getTarifaFijaBase(), minutosTranscurridos);
        double descuento = usuario.calcularDescuento(costoMinutos);
        double importeFinal = costoMinutos - descuento;
        if (importeFinal < 0) {
            importeFinal = 0;
        }

        // 5. Cobrar
        ProcesadorPago procesador = pagoFactory.crearProcesador(metodoPago);
        procesador.efectuarCobro(importeFinal);

        // 6. Retornar DTO de respuesta
        String tipoRodado = vehiculo.getClass().getSimpleName();
        String mensaje = String.format(Locale.US,
                "Viaje finalizado. Rodado: %s (Patente: %s). Tiempo: %d min. Monto cobrado por viaje: $%.2f",
                tipoRodado, vehiculo.getPatente(), minutosTranscurridos, importeFinal);

        return AlquilerResponseDTO.builder()
                .patente(vehiculo.getPatente())
                .costoFinal(importeFinal)
                .minutosTranscurridos(minutosTranscurridos)
                .estadoVehiculo(vehiculo.getNombreEstado())
                .mensaje(mensaje)
                .build();
    }

    public List<VehiculoResponseDTO> getVehiculosPorBateria() {
        List<Vehiculo> todos = getTodosLosVehiculos();

        // Ordenamiento natural (Comparable) - Bateria de menor a mayor
        java.util.Collections.sort(todos);

        List<VehiculoResponseDTO> resultado = new ArrayList<>();
        for (int i = 0; i < todos.size(); i++) {
            Vehiculo v = todos.get(i);
            resultado.add(VehiculoResponseDTO.builder()
                    .patente(v.getPatente())
                    .porcentajeBateria(v.getPorcentajeBateria())
                    .tarifaFijaBase(v.getTarifaFijaBase())
                    .estado(v.getNombreEstado())
                    .build());
        }
        return resultado;
    }

    public List<VehiculoResponseDTO> getVehiculosPorTarifa() {
        List<Vehiculo> todos = getTodosLosVehiculos();

        // Ordenamiento alternativo (Comparator) - Tarifa base de mayor a menor
        java.util.Collections.sort(todos, new CostoBaseVehiculoComparator());

        List<VehiculoResponseDTO> resultado = new ArrayList<>();
        for (int i = 0; i < todos.size(); i++) {
            Vehiculo v = todos.get(i);
            resultado.add(VehiculoResponseDTO.builder()
                    .patente(v.getPatente())
                    .porcentajeBateria(v.getPorcentajeBateria())
                    .tarifaFijaBase(v.getTarifaFijaBase())
                    .estado(v.getNombreEstado())
                    .build());
        }
        return resultado;
    }

    private List<Vehiculo> getTodosLosVehiculos() {
        List<Vehiculo> todos = new ArrayList<>();
        for (int i = 0; i < estaciones.size(); i++) {
            EstacionAnclaje estacion = estaciones.get(i);
            List<Vehiculo> vehiculosEstacion = estacion.getListaVehiculos();
            for (int j = 0; j < vehiculosEstacion.size(); j++) {
                todos.add(vehiculosEstacion.get(j));
            }
        }
        return todos;
    }
}
