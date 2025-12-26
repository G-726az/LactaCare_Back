package ista.M4A2.controllers;

import ista.M4A2.models.entity.DiasLaborablesEmpleado;
import ista.M4A2.models.services.serv.DiasLaborablesEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dias-laborables-empleado")
@CrossOrigin(origins = "*")
public class DiasLaborablesEmpleadoController {
    
    @Autowired
    private DiasLaborablesEmpleadoService diasLaborablesEmpleadoService;
    
    @GetMapping
    public ResponseEntity<List<DiasLaborablesEmpleado>> obtenerTodos() {
        try {
            List<DiasLaborablesEmpleado> dias = diasLaborablesEmpleadoService.obtenerTodos();
            return ResponseEntity.ok(dias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DiasLaborablesEmpleado> obtenerPorId(@PathVariable Long id) {
        try {
            DiasLaborablesEmpleado dias = diasLaborablesEmpleadoService.obtenerPorId(id);
            return ResponseEntity.ok(dias);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<DiasLaborablesEmpleado> crear(@RequestBody DiasLaborablesEmpleado dias) {
        try {
            DiasLaborablesEmpleado nuevosDias = diasLaborablesEmpleadoService.guardar(dias);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevosDias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DiasLaborablesEmpleado> actualizar(@PathVariable Long id, @RequestBody DiasLaborablesEmpleado dias) {
        try {
            DiasLaborablesEmpleado diasActualizados = diasLaborablesEmpleadoService.actualizar(id, dias);
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
            diasLaborablesEmpleadoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}