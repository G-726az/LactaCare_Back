package ista.M4A2.controllers;

import ista.M4A2.models.entity.DiasLaborablesSala;
import ista.M4A2.models.services.serv.DiasLaborablesSalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dias-laborables-sala")
@CrossOrigin(origins = "*")
public class DiasLaborablesSalaController {
    
    @Autowired
    private DiasLaborablesSalaService diasLaborablesSalaService;
    
    @GetMapping
    public ResponseEntity<List<DiasLaborablesSala>> obtenerTodos() {
        try {
            List<DiasLaborablesSala> dias = diasLaborablesSalaService.obtenerTodos();
            return ResponseEntity.ok(dias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DiasLaborablesSala> obtenerPorId(@PathVariable Long id) {
        try {
            DiasLaborablesSala dias = diasLaborablesSalaService.obtenerPorId(id);
            return ResponseEntity.ok(dias);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<DiasLaborablesSala> crear(@RequestBody DiasLaborablesSala dias) {
        try {
            DiasLaborablesSala nuevosDias = diasLaborablesSalaService.guardar(dias);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevosDias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DiasLaborablesSala> actualizar(@PathVariable Long id, @RequestBody DiasLaborablesSala dias) {
        try {
            DiasLaborablesSala diasActualizados = diasLaborablesSalaService.actualizar(id, dias);
            return ResponseEntity.ok(diasActualizados);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            diasLaborablesSalaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}