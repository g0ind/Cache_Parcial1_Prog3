package ar.edu.unlar.ecoride.controller;

import ar.edu.unlar.ecoride.dto.VehiculoResponseDTO;
import ar.edu.unlar.ecoride.service.AlquilerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final AlquilerService alquilerService;

    public VehiculoController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    @GetMapping("/prioridad-carga")
    public ResponseEntity<List<VehiculoResponseDTO>> porPrioridadCarga() {
        return ResponseEntity.ok(alquilerService.getVehiculosPorBateria());
    }

    @GetMapping("/tarifa-descendente")
    public ResponseEntity<List<VehiculoResponseDTO>> porTarifaDescendente() {
        return ResponseEntity.ok(alquilerService.getVehiculosPorTarifa());
    }
}
