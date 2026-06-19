package ar.edu.unlar.ecoride.controller;

import ar.edu.unlar.ecoride.dto.AlquilerRequestDTO;
import ar.edu.unlar.ecoride.dto.AlquilerResponseDTO;
import ar.edu.unlar.ecoride.dto.FinalizarRequestDTO;
import ar.edu.unlar.ecoride.service.AlquilerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    private final AlquilerService alquilerService;

    public AlquilerController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    @GetMapping("/desbloquear")
    public ResponseEntity<AlquilerResponseDTO> desbloquear(@RequestBody AlquilerRequestDTO request) {
        return ResponseEntity.ok(alquilerService.procesarDesbloqueo(
                request.getIdUsuario(),
                request.getPatente(),
                request.getMetodoPago()));
    }

    @PostMapping("/desbloquear")
    public ResponseEntity<AlquilerResponseDTO> desbloquearPost(@RequestBody AlquilerRequestDTO request) {
        return ResponseEntity.ok(alquilerService.procesarDesbloqueo(
                request.getIdUsuario(),
                request.getPatente(),
                request.getMetodoPago()));
    }

    @GetMapping("/desbloquear-params")
    public ResponseEntity<AlquilerResponseDTO> desbloquearConParams(
            @RequestParam String idUsuario,
            @RequestParam String patente,
            @RequestParam String metodoPago) {
        return ResponseEntity.ok(alquilerService.procesarDesbloqueo(idUsuario, patente, metodoPago));
    }

    @PostMapping("/finalizar")
    public ResponseEntity<AlquilerResponseDTO> finalizar(@RequestBody FinalizarRequestDTO request) {
        return ResponseEntity.ok(alquilerService.finalizarViaje(
                request.getPatente(),
                request.getMinutosTranscurridos(),
                request.getMetodoPago(),
                request.getIdUsuario()));
    }

    @GetMapping("/finalizar-params")
    public ResponseEntity<AlquilerResponseDTO> finalizarConParams(
            @RequestParam String patente,
            @RequestParam int minutosTranscurridos,
            @RequestParam String metodoPago,
            @RequestParam String idUsuario) {
        return ResponseEntity.ok(alquilerService.finalizarViaje(patente, minutosTranscurridos, metodoPago, idUsuario));
    }
}
