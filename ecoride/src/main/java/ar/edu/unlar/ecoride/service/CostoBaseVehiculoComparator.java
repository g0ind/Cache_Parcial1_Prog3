package ar.edu.unlar.ecoride.service;

import ar.edu.unlar.ecoride.model.Vehiculo;
import java.util.Comparator;

public class CostoBaseVehiculoComparator implements Comparator<Vehiculo> {
    @Override
    public int compare(Vehiculo o1, Vehiculo o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;
        // Orden descendente (de mayor a menor)
        return Double.compare(o2.getTarifaFijaBase(), o1.getTarifaFijaBase());
    }
}
