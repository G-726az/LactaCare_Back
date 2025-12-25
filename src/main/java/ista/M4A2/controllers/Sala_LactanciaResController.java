package ista.M4A2.controllers;

import ista.M4A2.models.entity.Sala_Lactancia;
import ista.M4A2.models.services.serv.ISala_LactanciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lactarios")
public class Sala_LactanciaResController {

    @Autowired
    private ISala_LactanciaService service;

    // GET: /api/lactarios
    @GetMapping
    public List<Sala_Lactancia> index() {
        return service.findAll();
    }
    
    // GET: /api/lactarios/{id}
    @GetMapping("/{id}")
    public Sala_Lactancia show(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST: /api/lactarios
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sala_Lactancia create(@RequestBody Sala_Lactancia entity) {
        return service.save(entity);
    }
    
    // PUT: /api/lactarios/{id}
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Sala_Lactancia update(@RequestBody Sala_Lactancia entity, @PathVariable Long id) { 
        Sala_Lactancia current = service.findById(id); 
        // Lógica de actualización de campos y relaciones
        current.setNombreCMedico(entity.getNombreCMedico());
        current.setDireccionCMedico(entity.getDireccionCMedico());
        current.setInstitucion(entity.getInstitucion());
        
        return service.save(current);
    }

    // DELETE: /api/lactarios/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
