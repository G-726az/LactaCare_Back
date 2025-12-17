package ista.M4A2.controllers;

import ista.M4A2.models.entity.Sistema;
import ista.M4A2.models.services.serv.SistemaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sistema")
@CrossOrigin(origins = "*")
public class SistemaRestController {
    
    @Autowired
    private SistemaService sistemaService;
    
    @GetMapping
    public ResponseEntity<List<Sistema>> obtenerTodos() {
        try {
            List<Sistema> sistemas = sistemaService.obtenerTodos();
            return ResponseEntity.ok(sistemas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Sistema> obtenerPorId(@PathVariable Integer id) {
        try {
            Sistema sistema = sistemaService.obtenerPorId(id);
            return ResponseEntity.ok(sistema);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Sistema> crear(@RequestBody Sistema sistema) {
        try {
            Sistema nuevoSistema = sistemaService.guardar(sistema);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSistema);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Sistema> actualizar(@PathVariable Integer id, @RequestBody Sistema sistema) {
        try {
            Sistema sistemaActualizado = sistemaService.actualizar(id, sistema);
            return ResponseEntity.ok(sistemaActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            sistemaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}