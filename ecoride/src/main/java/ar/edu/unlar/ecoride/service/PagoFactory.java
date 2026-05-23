package ar.edu.unlar.ecoride.service;

import org.springframework.stereotype.Component;

@Component
public class PagoFactory {

    public ProcesadorPago crearProcesador(String metodoPago) {
        if (metodoPago == null) {
            throw new IllegalArgumentException("El método de pago no puede ser nulo");
        }
        
        String normalizado = metodoPago.trim().toUpperCase();
        
        if (normalizado.equals("TARJETA")) {
            return new TarjetaCreditoProcesador();
        } else if (normalizado.equals("BILLETERA")) {
            return new BilleteraVirtualProcesador();
        } else {
            throw new IllegalArgumentException("Método de pago no soportado: " + metodoPago);
        }
    }
}
