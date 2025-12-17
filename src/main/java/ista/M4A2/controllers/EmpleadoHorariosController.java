package ista.M4A2.controllers;

import ista.M4A2.models.entity.EmpleadoHorarios;
import ista.M4A2.models.services.serv.EmpleadoHorariosService;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleado-horarios")
@CrossOrigin(origins = "*")
public class EmpleadoHorariosController {
    
    @Autowired
    private EmpleadoHorariosService empleadoHorariosService;
    
    @GetMapping
    public ResponseEntity<List<EmpleadoHorarios>> obtenerTodos() {
        try {
            List<EmpleadoHorarios> asignaciones = empleadoHorariosService.obtenerTodos();
            return ResponseEntity.ok(asignaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/empleado/{idEmpleado}")
    public ResponseEntity<List<EmpleadoHorarios>> obtenerPorEmpleado(@PathVariable Integer idEmpleado) {
        try {
            List<EmpleadoHorarios> asignaciones = empleadoHorariosService.obtenerPorEmpleado(idEmpleado);
            return ResponseEntity.ok(asignaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/horario/{idHorario}")
    public ResponseEntity<List<EmpleadoHorarios>> obtenerPorHorario(@PathVariable Integer idHorario) {
        try {
            List<EmpleadoHorarios> asignaciones = empleadoHorariosService.obtenerPorHorario(idHorario);
            return ResponseEntity.ok(asignaciones);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/existe/{idEmpleado}/{idHorario}")
    public ResponseEntity<Boolean> existeAsignacion(
            @PathVariable Integer idEmpleado, 
            @PathVariable Integer idHorario) {
        try {
            boolean existe = empleadoHorariosService.existeAsignacion(idEmpleado, idHorario);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/asignar/{idEmpleado}/{idHorario}")
    public ResponseEntity<EmpleadoHorarios> asignar(
            @PathVariable Integer idEmpleado, 
            @PathVariable Integer idHorario) {
        try {
            EmpleadoHorarios asignacion = empleadoHorariosService.asignar(idEmpleado, idHorario);
            return ResponseEntity.status(HttpStatus.CREATED).body(asignacion);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("ya existe")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{idEmpleado}/{idHorario}")
    public ResponseEntity<Void> eliminarAsignacion(
            @PathVariable Integer idEmpleado, 
            @PathVariable Integer idHorario) {
        try {
            empleadoHorariosService.eliminarAsignacion(idEmpleado, idHorario);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}