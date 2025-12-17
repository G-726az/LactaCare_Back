package ista.M4A2.controllers;

import ista.M4A2.models.entity.Ubicacion_Contenedor;
import ista.M4A2.models.services.serv.IUbicacion_ContenedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ubicacionescontenedor")
public class Ubicacion_ContenedorResController {

    @Autowired
    private IUbicacion_ContenedorService service;

    // GET: /api/ubicacionescontenedor
    @GetMapping
    public List<Ubicacion_Contenedor> index() {
        return service.findAll();
    }
    
    // GET: /api/ubicacionescontenedor/{id}
    @GetMapping("/{id}")
    public Ubicacion_Contenedor show(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST: /api/ubicacionescontenedor
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ubicacion_Contenedor create(@RequestBody Ubicacion_Contenedor entity) {
        return service.save(entity);
    }
    
    // PUT: /api/ubicacionescontenedor/{id}
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Ubicacion_Contenedor update(@RequestBody Ubicacion_Contenedor entity, @PathVariable Long id) { 
        Ubicacion_Contenedor current = service.findById(id); 
        // Lógica de actualización de la ubicación y relaciones
        current.setPisoRefrigerador(entity.getPisoRefrigerador());
        current.setFilaRefrigerador(entity.getFilaRefrigerador());
        current.setRefrigerador(entity.getRefrigerador()); // Actualiza la relación FK
        
        return service.save(current);
    }

    // DELETE: /api/ubicacionescontenedor/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}