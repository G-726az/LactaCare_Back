package ista.M4A2.controllers;

import ista.M4A2.models.entity.Dias_Laborables;
import ista.M4A2.models.services.serv.IDiasLaborablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/diaslaborables")
public class Dias_LaborablesResController {

    @Autowired
    private IDiasLaborablesService service;

    // GET: /api/diaslaborables
    @GetMapping
    public List<Dias_Laborables> index() {
        return service.findAll();
    }
    
    // GET: /api/diaslaborables/{id}
    @GetMapping("/{id}")
    public Dias_Laborables show(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST: /api/diaslaborables
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dias_Laborables create(@RequestBody Dias_Laborables entity) {
        return service.save(entity);
    }
    
    // PUT: /api/diaslaborables/{id}
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Dias_Laborables update(@RequestBody Dias_Laborables entity, @PathVariable Long id) { 
        Dias_Laborables current = service.findById(id); 
        // Lógica de actualización de campos (días individuales)
        current.setDiaLunes(entity.getDiaLunes());
        current.setDiaMartes(entity.getDiaMartes());
        // ... otros días
        
        return service.save(current);
    }

    // DELETE: /api/diaslaborables/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
