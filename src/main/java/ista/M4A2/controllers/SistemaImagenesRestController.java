package ista.M4A2.controllers;

import ista.M4A2.models.entity.SistemaImagenes;
import ista.M4A2.models.services.serv.SistemaImagenesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imagenes")
@CrossOrigin(origins = "*")
public class SistemaImagenesRestController {
    
    @Autowired
    private SistemaImagenesService imagenesService;
    
    @GetMapping
    public ResponseEntity<List<SistemaImagenes>> obtenerTodos() {
        try {
            List<SistemaImagenes> imagenes = imagenesService.obtenerTodos();
            return ResponseEntity.ok(imagenes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SistemaImagenes> obtenerPorId(@PathVariable Integer id) {
        try {
            SistemaImagenes imagen = imagenesService.obtenerPorId(id);
            return ResponseEntity.ok(imagen);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<SistemaImagenes> crear(@RequestBody SistemaImagenes imagen) {
        try {
            SistemaImagenes nuevaImagen = imagenesService.guardar(imagen);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaImagen);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SistemaImagenes> actualizar(@PathVariable Integer id, 
                                                       @RequestBody SistemaImagenes imagen) {
        try {
            SistemaImagenes imagenActualizada = imagenesService.actualizar(id, imagen);
            return ResponseEntity.ok(imagenActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            imagenesService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}