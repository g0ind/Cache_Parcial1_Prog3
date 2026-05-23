package ar.edu.unlar.ecoride.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VehiculoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleVehiculoNoEncontrado(VehiculoNoEncontradoException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Vehículo No Encontrado");
        response.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BateriaInsuficienteException.class)
    public ResponseEntity<Map<String, String>> handleBateriaInsuficiente(BateriaInsuficienteException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Batería Insuficiente");
        response.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Petición Incorrecta");
        response.put("mensaje", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
