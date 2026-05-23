package ar.edu.unlar.ecoride.controller;

import ar.edu.unlar.ecoride.dto.AlquilerRequestDTO;
import ar.edu.unlar.ecoride.service.AlquilerService;
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

    @GetMapping("/desbloquear")
    public ResponseEntity<Map<String, String>> desbloquear(@RequestBody AlquilerRequestDTO request) {
        return desbloquearInternal(request);
    }

    @PostMapping("/desbloquear")
    public ResponseEntity<Map<String, String>> desbloquearPost(@RequestBody AlquilerRequestDTO request) {
        return desbloquearInternal(request);
    }

    @GetMapping("/desbloquear-params")
    public ResponseEntity<Map<String, String>> desbloquearConParams(
            @RequestParam String idUsuario,
            @RequestParam String patente,
            @RequestParam String metodoPago) {
        return desbloquearInternal(new AlquilerRequestDTO(idUsuario, patente, metodoPago));
    }

    private ResponseEntity<Map<String, String>> desbloquearInternal(AlquilerRequestDTO request) {
        if (request == null || request.getIdUsuario() == null || request.getPatente() == null
                || request.getMetodoPago() == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Faltan parámetros requeridos: 'idUsuario', 'patente' y 'metodoPago'.");
            return ResponseEntity.badRequest().body(response);
        }
        String resultado = alquilerService.procesarDesbloqueo(
                request.getIdUsuario(),
                request.getPatente(),
                request.getMetodoPago());
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", resultado);
        return ResponseEntity.ok(response);
    }
}
