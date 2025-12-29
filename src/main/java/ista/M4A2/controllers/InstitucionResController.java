package ista.M4A2.controllers;

import ista.M4A2.models.entity.Institucion;
import ista.M4A2.models.services.serv.IInstitucionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/instituciones")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class InstitucionResController {

    @Autowired
    private IInstitucionService institucionService;

    // GET: /api/instituciones
    @GetMapping
    public List<Institucion> index() {
        return institucionService.findAll();
    }
    
    // GET: /api/instituciones/{id}
    @GetMapping("/{id}")
    public Institucion show(@PathVariable Long id) {
        return institucionService.findById(id);
    }

    // POST: /api/instituciones
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Institucion create(@RequestBody Institucion entity) {
        return institucionService.save(entity);
    }
    
    // PUT: /api/instituciones/{id}
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Institucion update(@RequestBody Institucion entity, @PathVariable Long id) { 
        Institucion current = institucionService.findById(id); 
        // Lógica de actualización de campos (ej. Nombre, Logo)
        current.setNombreInstitucion(entity.getNombreInstitucion());
        current.setLogoInstitucion(entity.getLogoInstitucion());
        
        return institucionService.save(current);
    }

    // DELETE: /api/instituciones/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        institucionService.deleteById(id);
    }
}