package ista.M4A2.controllers;

import ista.M4A2.models.entity.Refrigerador;
import ista.M4A2.models.services.serv.IRefrigeradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/refrigeradores")
public class RefrigeradorResController {

    @Autowired
    private IRefrigeradorService service;

    // GET: /api/refrigeradores
    @GetMapping
    public List<Refrigerador> index() {
        return service.findAll();
    }
    
    // GET: /api/refrigeradores/{id}
    @GetMapping("/{id}")
    public Refrigerador show(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST: /api/refrigeradores
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Refrigerador create(@RequestBody Refrigerador entity) {
        return service.save(entity);
    }
    
    // PUT: /api/refrigeradores/{id}
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Refrigerador update(@RequestBody Refrigerador entity, @PathVariable Long id) { 
        Refrigerador current = service.findById(id); 
        // Lógica de actualización de campos
        current.setFilaRefrigerador(entity.getFilaRefrigerador());
        current.setColumnaRefrigerador(entity.getColumnaRefrigerador());
        current.setSala_lactancia(entity.getSala_lactancia()); // Actualiza la relación FK
        
        return service.save(current);
    }

    // DELETE: /api/refrigeradores/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}