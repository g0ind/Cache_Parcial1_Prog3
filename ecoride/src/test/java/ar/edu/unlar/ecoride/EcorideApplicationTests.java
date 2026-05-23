package ar.edu.unlar.ecoride;

import ar.edu.unlar.ecoride.controller.AlquilerController;
import ar.edu.unlar.ecoride.dto.AlquilerRequestDTO;
import ar.edu.unlar.ecoride.exception.BateriaInsuficienteException;
import ar.edu.unlar.ecoride.exception.VehiculoNoEncontradoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EcorideApplicationTests {

	@Autowired
	private AlquilerController alquilerController;

	@Test
	void contextLoads() {
		assertNotNull(alquilerController);
	}

	@Test
	void testDesbloqueoExitosoUsuarioRegular() {
		AlquilerRequestDTO request = new AlquilerRequestDTO("1", "MONO1", "TARJETA");
		ResponseEntity<Map<String, String>> response = alquilerController.desbloquear(request);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		String mensaje = response.getBody().get("mensaje");
		assertTrue(mensaje.contains("Desbloqueo exitoso"));
		assertTrue(mensaje.contains("MONO1"));
		assertTrue(mensaje.contains("Monto cobrado: $100.00"));
	}

	@Test
	void testDesbloqueoExitosoUsuarioPremium() {
		// Premium user (ID "2") has 15% discount on base fee ($200.0 for BICI1)
		// Expected amount = $200.0 * (1 - 0.15) = $170.00
		AlquilerRequestDTO request = new AlquilerRequestDTO("2", "BICI1", "BILLETERA");
		ResponseEntity<Map<String, String>> response = alquilerController.desbloquear(request);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		String mensaje = response.getBody().get("mensaje");
		assertTrue(mensaje.contains("Desbloqueo exitoso"));
		assertTrue(mensaje.contains("BICI1"));
		assertTrue(mensaje.contains("Monto cobrado: $170.00"));
	}

	@Test
	void testDesbloqueoVehiculoNoEncontrado() {
		AlquilerRequestDTO request = new AlquilerRequestDTO("1", "INEXISTENTE", "TARJETA");
		
		assertThrows(VehiculoNoEncontradoException.class, () -> {
			alquilerController.desbloquear(request);
		});
	}

	@Test
	void testDesbloqueoBateriaInsuficiente() {
		AlquilerRequestDTO request = new AlquilerRequestDTO("1", "MONO_BAJO", "TARJETA");
		
		assertThrows(BateriaInsuficienteException.class, () -> {
			alquilerController.desbloquear(request);
		});
	}
}


