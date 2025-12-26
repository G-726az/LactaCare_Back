package ista.M4A2.controllers;

import ista.M4A2.models.entity.HorariosEmpleado;
import ista.M4A2.models.services.serv.HorariosEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios-empleado")
@CrossOrigin(origins = "*")
public class HorariosEmpleadoController {
    
    @Autowired
    private HorariosEmpleadoService horariosEmpleadoService;
    
    @GetMapping
    public ResponseEntity<List<HorariosEmpleado>> obtenerTodos() {
        try {
            List<HorariosEmpleado> horarios = horariosEmpleadoService.obtenerTodos();
            return ResponseEntity.ok(horarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HorariosEmpleado> obtenerPorId(@PathVariable Integer id) {
        try {
            HorariosEmpleado horario = horariosEmpleadoService.obtenerPorId(id);
            return ResponseEntity.ok(horario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<HorariosEmpleado> crear(@RequestBody HorariosEmpleado horario) {
        try {
            HorariosEmpleado nuevoHorario = horariosEmpleadoService.guardar(horario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoHorario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<HorariosEmpleado> actualizar(@PathVariable Integer id, @RequestBody HorariosEmpleado horario) {
        try {
            HorariosEmpleado horarioActualizado = horariosEmpleadoService.actualizar(id, horario);
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
            horariosEmpleadoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}