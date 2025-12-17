package ista.M4A2.controllers;

import ista.M4A2.models.entity.Estado_Refrigerador;
import ista.M4A2.models.services.serv.IEstado_RefrigeradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/estadosrefrigerador")
public class Estado_RefrigeradorResController {

    @Autowired
    private IEstado_RefrigeradorService service;

    // GET: /api/estadosrefrigerador
    @GetMapping
    public List<Estado_Refrigerador> index() {
        return service.findAll();
    }
    
    // GET: /api/estadosrefrigerador/{id}
    @GetMapping("/{id}")
    public Estado_Refrigerador show(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST: /api/estadosrefrigerador
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado_Refrigerador create(@RequestBody Estado_Refrigerador entity) {
        return service.save(entity);
    }
    
    // PUT: /api/estadosrefrigerador/{id}
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Estado_Refrigerador update(@RequestBody Estado_Refrigerador entity, @PathVariable Long id) { 
        Estado_Refrigerador current = service.findById(id); 
        // Lógica de actualización de campos de medición
        current.setTemperaturaEstRefrigerador(entity.getTemperaturaEstRefrigerador());
        current.setHumedadEstRefrigerador(entity.getHumedadEstRefrigerador());
        current.setRefrigerador(entity.getRefrigerador()); // Actualiza la relación FK
        
        return service.save(current);
    }

    // DELETE: /api/estadosrefrigerador/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}