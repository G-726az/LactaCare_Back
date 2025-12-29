package ista.M4A2.controllers;

import ista.M4A2.models.dto.SalaLactanciaConCubiculosDTO;
import ista.M4A2.models.entity.Sala_Lactancia;
import ista.M4A2.models.services.serv.ISala_LactanciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lactarios")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {
    RequestMethod.GET, 
    RequestMethod.POST, 
    RequestMethod.PUT, 
    RequestMethod.DELETE, 
    RequestMethod.PATCH
})
public class Sala_LactanciaResController {

    @Autowired
    private ISala_LactanciaService service;

    /**
     * Obtener todas las salas de lactancia
     * GET /api/lactarios
     */
    @GetMapping
    public ResponseEntity<List<Sala_Lactancia>> index() {
        try {
            List<Sala_Lactancia> salas = service.findAll();
            return ResponseEntity.ok(salas);
        } catch (Exception e) {
            System.err.println("Error al obtener salas: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener solo salas activas
     * GET /api/lactarios/activos
     */
    @GetMapping("/activos")
    public ResponseEntity<List<Sala_Lactancia>> indexActive() {
        try {
            List<Sala_Lactancia> salas = service.findAllActive();
            return ResponseEntity.ok(salas);
        } catch (Exception e) {
            System.err.println("Error al obtener salas activas: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener una sala por ID
     * GET /api/lactarios/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Sala_Lactancia> show(@PathVariable Long id) {
        try {
            Sala_Lactancia sala = service.findById(id);
            if (sala == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(sala);
        } catch (Exception e) {
            System.err.println("Error al obtener sala con ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear una nueva sala de lactancia (sin cubículos)
     * POST /api/lactarios
     */
    @PostMapping
    public ResponseEntity<Sala_Lactancia> create(@RequestBody Sala_Lactancia entity) {
        try {
            Sala_Lactancia savedSala = service.save(entity);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSala);
        } catch (Exception e) {
            System.err.println("Error al crear sala: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crear sala con cubículos
     * POST /api/lactarios/con-cubiculos
     */
    @PostMapping("/con-cubiculos")
    public ResponseEntity<?> createConCubiculos(
            @Valid @RequestBody SalaLactanciaConCubiculosDTO dto,
            BindingResult result) {
        
        Map<String, Object> response = new HashMap<>();
        
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> err.getField() + ": " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        try {
            Sala_Lactancia salaNew = service.saveConCubiculos(
                dto.getSalaLactancia(),
                dto.getNumeroCubiculos()
            );
            
            response.put("mensaje", "Sala de lactancia creada con " 
                + dto.getNumeroCubiculos() + " cubículos exitosamente");
            response.put("sala", salaNew);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
            
        } catch (Exception e) {
            System.err.println("Error al crear sala con cubículos: " + e.getMessage());
            e.printStackTrace();
            response.put("mensaje", "Error al crear la sala de lactancia");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualizar una sala existente
     * PUT /api/lactarios/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sala_Lactancia> update(
            @RequestBody Sala_Lactancia entity,
            @PathVariable Long id) {

        try {
            Sala_Lactancia current = service.findById(id);
            if (current == null) {
                return ResponseEntity.notFound().build();
            }

            current.setNombreCMedico(entity.getNombreCMedico());
            current.setDireccionCMedico(entity.getDireccionCMedico());
            current.setCorreoCMedico(entity.getCorreoCMedico());
            current.setTelefonoCMedico(entity.getTelefonoCMedico());
            current.setLatitudCMedico(entity.getLatitudCMedico());
            current.setLongitudCMedico(entity.getLongitudCMedico());
            current.setInstitucion(entity.getInstitucion());
            current.setHorarioSala(entity.getHorarioSala());
            current.setDiasLaborablesSala(entity.getDiasLaborablesSala());

            if (entity.getEstado() != null) {
                current.setEstado(entity.getEstado());
            }

            Sala_Lactancia updatedSala = service.save(current);
            return ResponseEntity.ok(updatedSala);
            
        } catch (Exception e) {
            System.err.println("Error al actualizar sala con ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Eliminar una sala (eliminación física)
     * DELETE /api/lactarios/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        try {
            Sala_Lactancia sala = service.findById(id);
            if (sala == null) {
                return ResponseEntity.notFound().build();
            }
            
            service.deleteById(id);
            
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Sala eliminada exitosamente");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println("Error al eliminar sala con ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error al eliminar la sala");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Desactivar una sala (soft delete)
     * PATCH /api/lactarios/{id}/desactivar
     */
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Sala_Lactancia> softDelete(@PathVariable Long id) {
        try {
            service.softDeleteById(id);
            Sala_Lactancia sala = service.findById(id);
            
            if (sala == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(sala);
            
        } catch (Exception e) {
            System.err.println("Error al desactivar sala con ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Activar una sala
     * PATCH /api/lactarios/{id}/activar
     */
    @PatchMapping("/{id}/activar")
    public ResponseEntity<Sala_Lactancia> activate(@PathVariable Long id) {
        try {
            service.activateById(id);
            Sala_Lactancia sala = service.findById(id);
            
            if (sala == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(sala);
            
        } catch (Exception e) {
            System.err.println("Error al activar sala con ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}