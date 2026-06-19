package ar.edu.unlar.ecoride.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstacionAnclaje {
    private String nombreUnico;

    @Builder.Default
    private Map<String, Vehiculo> vehiculos = new HashMap<>();

    public void agregarVehiculo(Vehiculo vehiculo) {
        if (this.vehiculos == null) {
            this.vehiculos = new HashMap<>();
        }
        if (vehiculo != null && vehiculo.getPatente() != null) {
            this.vehiculos.put(vehiculo.getPatente().toUpperCase(), vehiculo);
        }
    }

    public Vehiculo buscarVehiculoPorPatente(String patente) {
        if (patente == null || this.vehiculos == null) {
            return null;
        }
        return this.vehiculos.get(patente.toUpperCase());
    }

    public List<Vehiculo> getListaVehiculos() {
        if (this.vehiculos == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(this.vehiculos.values());
    }
}
