package ista.M4A2.controllers;

import ista.M4A2.models.entity.SistemaSugerencias;
import ista.M4A2.models.services.serv.SistemaSugerenciasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sugerencias")
@CrossOrigin(origins = "*")
public class SistemaSugerenciasRestController {
    
    @Autowired
    private SistemaSugerenciasService sugerenciasService;
    
    @GetMapping
    public ResponseEntity<List<SistemaSugerencias>> obtenerTodos() {
        try {
            List<SistemaSugerencias> sugerencias = sugerenciasService.obtenerTodos();
            return ResponseEntity.ok(sugerencias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SistemaSugerencias> obtenerPorId(@PathVariable Integer id) {
        try {
            SistemaSugerencias sugerencia = sugerenciasService.obtenerPorId(id);
            return ResponseEntity.ok(sugerencia);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<SistemaSugerencias> crear(@RequestBody SistemaSugerencias sugerencia) {
        try {
            SistemaSugerencias nuevaSugerencia = sugerenciasService.guardar(sugerencia);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSugerencia);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SistemaSugerencias> actualizar(@PathVariable Integer id, 
                                                          @RequestBody SistemaSugerencias sugerencia) {
        try {
            SistemaSugerencias sugerenciaActualizada = sugerenciasService.actualizar(id, sugerencia);
            return ResponseEntity.ok(sugerenciaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            sugerenciasService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}