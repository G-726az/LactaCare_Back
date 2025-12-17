package ista.M4A2.controllers;

import ista.M4A2.config.EcuadorianValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/validacion")
@CrossOrigin(origins = "*")
public class ValidationRestController {

    @Autowired
    private EcuadorianValidator validator;

    @PostMapping("/cedula")
    public ResponseEntity<Map<String, Object>> validarCedula(@RequestBody Map<String, String> request) {
        String cedula = request.get("cedula");
        Map<String, Object> response = new HashMap<>();

        boolean isValid = validator.validarCedula(cedula);
        
        response.put("valid", isValid); 
        response.put("type", "cedula");
        response.put("message", isValid ? "Cédula válida" : "Cédula inválida");
        response.put("cedula", cedula);

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para validar RUC
     */
    @PostMapping("/ruc")
    public ResponseEntity<Map<String, Object>> validarRUC(@RequestBody Map<String, String> request) {
        String ruc = request.get("ruc");
        Map<String, Object> response = new HashMap<>();

        boolean isValid = validator.validarRUC(ruc);
        
        response.put("valid", isValid);
        response.put("type", "ruc");
        response.put("message", isValid ? "RUC válido" : "RUC inválido. Debe ser cédula + 001");
        response.put("ruc", ruc);

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para validar correo electrónico
     */
    @PostMapping("/email")
    public ResponseEntity<Map<String, Object>> validarEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Map<String, Object> response = new HashMap<>();

        boolean isValid = validator.validarCorreoElectronico(email);
        
        response.put("valid", isValid);
        response.put("type", "email");
        response.put("message", isValid ? 
            "Correo válido" : 
            "Correo inválido. Solo se permiten dominios: @gmail.com, @hotmail.com, @tecazuay.edu.ec");
        response.put("email", email);

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para generar RUC desde una cédula
     */
    @PostMapping("/generar-ruc")
    public ResponseEntity<Map<String, Object>> generarRUC(@RequestBody Map<String, String> request) {
        String cedula = request.get("cedula");
        Map<String, Object> response = new HashMap<>();

        String ruc = validator.generarRUC(cedula);
        
        if (ruc != null) {
            response.put("success", true);
            response.put("cedula", cedula);
            response.put("ruc", ruc);
            response.put("message", "RUC generado correctamente");
        } else {
            response.put("success", false);
            response.put("cedula", cedula);
            response.put("message", "No se pudo generar el RUC. Cédula inválida");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para validar múltiples campos a la vez
     */
    @PostMapping("/validar-todo")
    public ResponseEntity<Map<String, Object>> validarTodo(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> validaciones = new HashMap<>();

        // Validar cédula si existe
        if (request.containsKey("cedula") && request.get("cedula") != null) {
            String cedula = request.get("cedula");
            validaciones.put("cedula", Map.of(
                "valid", validator.validarCedula(cedula),
                "value", cedula
            ));
        }

        // Validar RUC si existe
        if (request.containsKey("ruc") && request.get("ruc") != null) {
            String ruc = request.get("ruc");
            validaciones.put("ruc", Map.of(
                "valid", validator.validarRUC(ruc),
                "value", ruc
            ));
        }

        // Validar email si existe
        if (request.containsKey("email") && request.get("email") != null) {
            String email = request.get("email");
            validaciones.put("email", Map.of(
                "valid", validator.validarCorreoElectronico(email),
                "value", email
            ));
        }

        response.put("validaciones", validaciones);
        response.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(response);
    }
}