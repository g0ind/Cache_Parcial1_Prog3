package ar.edu.unlar.ecoride.service;

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

    public AlquilerService(PagoFactory pagoFactory) {
        this.pagoFactory = pagoFactory;
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

    public String procesarDesbloqueo(String idUsuario, String patente, String metodoPago) {
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

        // 2. Localizar el vehículo dentro de las estaciones a través de su patente
        // (Traditional element-by-element search as required)
        Vehiculo vehiculo = null;
        for (int i = 0; i < estaciones.size(); i++) {
            EstacionAnclaje estacion = estaciones.get(i);
            Vehiculo encontrado = estacion.buscarVehiculoPorPatente(patente);
            if (encontrado != null) {
                vehiculo = encontrado;
                break;
            }
        }

        // Si no figura en la lista de la estación, lanzar alarma
        if (vehiculo == null) {
            throw new VehiculoNoEncontradoException("Vehículo No Encontrado");
        }

        // 3. Validar nivel de batería (mínimo 15%)
        if (vehiculo.getPorcentajeBateria() < 15) {
            throw new BateriaInsuficienteException("Batería Insuficiente");
        }

        // 4. Calcular el importe final considerando las características del usuario (descuento)
        double totalBase = vehiculo.getTarifaFijaBase();
        double descuento = usuario.calcularDescuento(totalBase);
        double importeFinal = totalBase - descuento;

        // 5. Obtener el medio de pago adecuado a través del componente de creación de pagos
        ProcesadorPago procesador = pagoFactory.crearProcesador(metodoPago);

        // 6. Efectuar el cobro
        procesador.efectuarCobro(importeFinal);

        // 7. Retornar una respuesta exitosa detallando el rodado desbloqueado y el monto cobrado
        String tipoRodado = vehiculo.getClass().getSimpleName();
        return String.format(Locale.US, "Desbloqueo exitoso. Rodado: %s (Patente: %s). Monto cobrado: $%.2f", 
                tipoRodado, vehiculo.getPatente(), importeFinal);
    }
}
