package ar.edu.unlar.ecoride.service;

import java.util.Locale;

public class TarjetaCreditoProcesador implements ProcesadorPago {

    @Override
    public void efectuarCobro(double importe) {
        System.out.printf(Locale.US, "Cobro exitoso de $%.2f realizado con Tarjeta de Crédito%n", importe);
    }
}
