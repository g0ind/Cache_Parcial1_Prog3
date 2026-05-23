package ar.edu.unlar.ecoride.config;

import ar.edu.unlar.ecoride.model.BicicletaElectrica;
import ar.edu.unlar.ecoride.model.EstacionAnclaje;
import ar.edu.unlar.ecoride.model.Monopatin;
import ar.edu.unlar.ecoride.model.UsuarioPremium;
import ar.edu.unlar.ecoride.model.UsuarioRegular;
import ar.edu.unlar.ecoride.service.AlquilerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(AlquilerService alquilerService) {
        return args -> {
            // Seed Users
            alquilerService.agregarUsuario(UsuarioRegular.builder()
                    .id("1")
                    .nombreCompleto("Juan Perez")
                    .build());

            alquilerService.agregarUsuario(UsuarioPremium.builder()
                    .id("2")
                    .nombreCompleto("Ana Gomez")
                    .descuentoFijo(0.15) // 15% discount (default value from diagram)
                    .build());

            // Seed Stations & Vehicles
            EstacionAnclaje centro = EstacionAnclaje.builder()
                    .nombreUnico("Centro")
                    .vehiculos(new ArrayList<>())
                    .build();

            centro.getVehiculos().add(Monopatin.builder()
                    .patente("MONO1")
                    .porcentajeBateria(80)
                    .tarifaFijaBase(100.0)
                    .amortiguacionReforzada(true)
                    .build());

            centro.getVehiculos().add(BicicletaElectrica.builder()
                    .patente("BICI1")
                    .porcentajeBateria(50)
                    .tarifaFijaBase(200.0)
                    .capacidadCanasto(1500)
                    .build());

            centro.getVehiculos().add(Monopatin.builder()
                    .patente("MONO_BAJO")
                    .porcentajeBateria(10) // less than 15% to trigger battery exception
                    .tarifaFijaBase(100.0)
                    .amortiguacionReforzada(false)
                    .build());

            alquilerService.agregarEstacion(centro);

            EstacionAnclaje norte = EstacionAnclaje.builder()
                    .nombreUnico("Norte")
                    .vehiculos(new ArrayList<>())
                    .build();

            norte.getVehiculos().add(BicicletaElectrica.builder()
                    .patente("BICI2")
                    .porcentajeBateria(95)
                    .tarifaFijaBase(250.0)
                    .capacidadCanasto(2000)
                    .build());

            alquilerService.agregarEstacion(norte);

            System.out.println("======================================================================");
            System.out.println("                  🌿 EcoRide App Startup Summary 🌿");
            System.out.println("======================================================================");
            System.out.println("  🌐 Navegador (Web Test Interface):");
            System.out.println("     👉 http://localhost:8080/");
            System.out.println("");
            System.out.println("  🔌 Endpoints Disponibles para Pruebas:");
            System.out.println("     • GET  /api/alquileres/desbloquear        (Requiere JSON body)");
            System.out.println("     • POST /api/alquileres/desbloquear        (Requiere JSON body)");
            System.out.println("     • GET  /api/alquileres/desbloquear-params (Usa Query Params)");
            System.out.println("            Ejemplo: http://localhost:8080/api/alquileres/desbloquear-params?idUsuario=1&patente=MONO1&metodoPago=TARJETA");
            System.out.println("");
            System.out.println("  👥 Usuarios Registrados:");
            System.out.println("     • ID [1]: Juan Perez (Usuario Regular)");
            System.out.println("     • ID [2]: Ana Gomez (Usuario Premium - 15% Descuento)");
            System.out.println("");
            System.out.println("  🚲 Vehículos y Ubicaciones:");
            System.out.println("     • Estación Centro:");
            System.out.println("       - Patente [MONO1]     - Monopatín (Batería 80%, Base $100.0)");
            System.out.println("       - Patente [BICI1]     - Bicicleta (Batería 50%, Base $200.0)");
            System.out.println("       - Patente [MONO_BAJO] - Monopatín (Batería 10% - Carga insuficiente)");
            System.out.println("     • Estación Norte:");
            System.out.println("       - Patente [BICI2]     - Bicicleta (Batería 95%, Base $250.0)");
            System.out.println("");
            System.out.println("  💳 Métodos de Pago Válidos:");
            System.out.println("     • TARJETA, BILLETERA");
            System.out.println("======================================================================");
        };
    }
}
