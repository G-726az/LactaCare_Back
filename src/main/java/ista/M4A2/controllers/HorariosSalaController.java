package ista.M4A2.controllers;

import ista.M4A2.models.entity.HorariosSala;
import ista.M4A2.models.services.serv.HorariosSalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios-sala")
@CrossOrigin(origins = "*")
public class HorariosSalaController {
    
    @Autowired
    private HorariosSalaService horariosSalaService;
    
    @GetMapping
    public ResponseEntity<List<HorariosSala>> obtenerTodos() {
        try {
            List<HorariosSala> horarios = horariosSalaService.obtenerTodos();
            return ResponseEntity.ok(horarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HorariosSala> obtenerPorId(@PathVariable Integer id) {
        try {
            HorariosSala horario = horariosSalaService.obtenerPorId(id);
            return ResponseEntity.ok(horario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<HorariosSala> crear(@RequestBody HorariosSala horario) {
        try {
            HorariosSala nuevoHorario = horariosSalaService.guardar(horario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoHorario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<HorariosSala> actualizar(@PathVariable Integer id, @RequestBody HorariosSala horario) {
        try {
            HorariosSala horarioActualizado = horariosSalaService.actualizar(id, horario);
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
            horariosSalaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}