package ista.M4A2.controllers;

import ista.M4A2.models.entity.Horarios;
import ista.M4A2.models.services.serv.HorariosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin(origins = "*")
public class HorariosRestController {
    
    @Autowired
    private HorariosService horariosService;
    
    @GetMapping
    public ResponseEntity<List<Horarios>> obtenerTodos() {
        try {
            List<Horarios> horarios = horariosService.obtenerTodos();
            return ResponseEntity.ok(horarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Horarios> obtenerPorId(@PathVariable Integer id) {
        try {
            Horarios horario = horariosService.obtenerPorId(id);
            return ResponseEntity.ok(horario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Horarios> crear(@RequestBody Horarios horario) {
        try {
            Horarios nuevoHorario = horariosService.guardar(horario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoHorario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Horarios> actualizar(@PathVariable Integer id, @RequestBody Horarios horario) {
        try {
            Horarios horarioActualizado = horariosService.actualizar(id, horario);
            return ResponseEntity.ok(horarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            horariosService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}