    package ar.edu.unlar.ecoride.model;

    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.ArrayList;
    import java.util.List;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class EstacionAnclaje {
        private String nombreUnico;
        
        @Builder.Default
        private List<Vehiculo> vehiculos = new ArrayList<>();

        public Vehiculo buscarVehiculoPorPatente(String patente) {
            if (patente == null || this.vehiculos == null) {
                return null;
            }
            for (int i = 0; i < this.vehiculos.size(); i++) {
                Vehiculo v = this.vehiculos.get(i);
                if (patente.equalsIgnoreCase(v.getPatente())) {
                    return v;
                }
            }
            return null;
        }
    }
