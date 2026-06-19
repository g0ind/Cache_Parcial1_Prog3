package ar.edu.unlar.ecoride.controller;

import ar.edu.unlar.ecoride.dto.AlquilerRequestDTO;
import ar.edu.unlar.ecoride.dto.AlquilerResponseDTO;
import ar.edu.unlar.ecoride.dto.FinalizarRequestDTO;
import ar.edu.unlar.ecoride.service.AlquilerService;
import ar.edu.unlar.ecoride.service.CriterioEstandar;
import ar.edu.unlar.ecoride.service.CriterioHoraPico;
import ar.edu.unlar.ecoride.service.CriterioTemporal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    private final AlquilerService alquilerService;

    public AlquilerController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    @PostMapping("/desbloquear")
    public ResponseEntity<AlquilerResponseDTO> desbloquear(@RequestBody AlquilerRequestDTO request) {
        return ResponseEntity.ok(alquilerService.procesarDesbloqueo(
                request.getIdUsuario(),
                request.getPatente(),
                request.getMetodoPago()));
    }

    @PostMapping("/finalizar")
    public ResponseEntity<AlquilerResponseDTO> finalizar(@RequestBody FinalizarRequestDTO request) {
        return ResponseEntity.ok(alquilerService.finalizarViaje(
                request.getPatente(),
                request.getMinutosTranscurridos(),
                request.getMetodoPago(),
                request.getIdUsuario()));
    }

    @PostMapping("/tarifa")
    public ResponseEntity<Map<String, String>> cambiarCriterio(@RequestParam String criterio) {
        Map<String, String> response = new HashMap<>();
        if ("HORA_PICO".equalsIgnoreCase(criterio)) {
            alquilerService.getGestorTarifa().setCriterio(new CriterioHoraPico());
            response.put("mensaje", "Criterio de facturación cambiado a: Hora Pico (Recargo 40%)");
        } else if ("TEMPORAL".equalsIgnoreCase(criterio)) {
            alquilerService.getGestorTarifa().setCriterio(new CriterioTemporal());
            response.put("mensaje", "Criterio de facturación cambiado a: Temporal Climático (Recargo fijo $150)");
        } else {
            alquilerService.getGestorTarifa().setCriterio(new CriterioEstandar());
            response.put("mensaje", "Criterio de facturación cambiado a: Estándar (Sin recargos)");
        }
        return ResponseEntity.ok(response);
    }
}
