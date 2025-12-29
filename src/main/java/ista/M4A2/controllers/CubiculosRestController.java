package ista.M4A2.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ista.M4A2.models.entity.Cubiculos;
import ista.M4A2.models.services.serv.ICubiculosService;
import jakarta.validation.Valid;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class CubiculosRestController {

    @Autowired
    private ICubiculosService cubiculosService;

    // Listar todos los cubículos
    @GetMapping("/cubiculos")
    public List<Cubiculos> index() {
        return cubiculosService.findAll();
    }

    // Obtener cubículo por ID
    @GetMapping("/cubiculos/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Cubiculos cubiculo = null;
        Map<String, Object> response = new HashMap<>();
        
        try {
            cubiculo = cubiculosService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        if (cubiculo == null) {
            response.put("mensaje", "El cubículo ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Cubiculos>(cubiculo, HttpStatus.OK);
    }

    // Crear nuevo cubículo
    @PostMapping("/cubiculos")
    public ResponseEntity<?> create(@Valid @RequestBody Cubiculos cubiculo, BindingResult result) {
        Cubiculos cubiculoNew = null;
        Map<String, Object> response = new HashMap<>();
        
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        
        try {
            cubiculoNew = cubiculosService.save(cubiculo);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "El cubículo ha sido creado con éxito!");
        response.put("cubiculo", cubiculoNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    // Actualizar cubículo
    @PutMapping("/cubiculos/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cubiculos cubiculo, BindingResult result, @PathVariable Long id) {
        Cubiculos cubiculoActual = cubiculosService.findById(id);
        Cubiculos cubiculoUpdated = null;
        Map<String, Object> response = new HashMap<>();
        
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        
        if (cubiculoActual == null) {
            response.put("mensaje", "Error: no se pudo editar, el cubículo ID: "
                    .concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        try {
            cubiculoActual.setNombreCb(cubiculo.getNombreCb());
            cubiculoActual.setEstadoCb(cubiculo.getEstadoCb());
            cubiculoActual.setSalaLactancia(cubiculo.getSalaLactancia());
            
            cubiculoUpdated = cubiculosService.save(cubiculoActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el cubículo en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "El cubículo ha sido actualizado con éxito!");
        response.put("cubiculo", cubiculoUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    // Eliminar cubículo
    @DeleteMapping("/cubiculos/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Cubiculos cubiculo = cubiculosService.findById(id);
            if (cubiculo == null) {
                response.put("mensaje", "Error: el cubículo ID: "
                        .concat(id.toString().concat(" no existe en la base de datos!")));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
            
            cubiculosService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el cubículo de la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "El cubículo ha sido eliminado con éxito!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    // Buscar cubículos por sala de lactancia
    @GetMapping("/cubiculos/sala/{idLactario}")
    public ResponseEntity<?> findBySala(@PathVariable Long idLactario) {
        List<Cubiculos> cubiculos = null;
        Map<String, Object> response = new HashMap<>();
        
        try {
            cubiculos = cubiculosService.findBySalaLactanciaId(idLactario);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al consultar cubículos por sala");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        if (cubiculos.isEmpty()) {
            response.put("mensaje", "No se encontraron cubículos para la sala ID: " + idLactario);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<List<Cubiculos>>(cubiculos, HttpStatus.OK);
    }

    // Buscar cubículos disponibles por sala
    @GetMapping("/cubiculos/sala/{idLactario}/disponibles")
    public ResponseEntity<?> findDisponiblesBySala(@PathVariable Long idLactario) {
        List<Cubiculos> cubiculos = null;
        Map<String, Object> response = new HashMap<>();
        
        try {
            cubiculos = cubiculosService.findDisponiblesBySala(idLactario, "Disponible");
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al consultar cubículos disponibles");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<List<Cubiculos>>(cubiculos, HttpStatus.OK);
    }
}