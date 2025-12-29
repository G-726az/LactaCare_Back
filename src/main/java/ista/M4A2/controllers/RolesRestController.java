package ista.M4A2.controllers;

import ista.M4A2.models.dto.RolDTO;
import ista.M4A2.models.entity.Roles;
import ista.M4A2.models.services.serv.RolesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolesRestController {
    
	@Autowired
    private RolesService rolesService;
    
    // --- METODO AUXILIAR PARA CONVERTIR ENTIDAD A DTO ---
    // Esto evita el error LazyInitializationException al ignorar la lista de empleados
    private RolDTO mapToDto(Roles rol) {
        return new RolDTO(rol.getIdRoles(), rol.getNombreRol());
    }
    // ----------------------------------------------------

    @GetMapping
    public ResponseEntity<List<RolDTO>> obtenerTodos() {
        try {
            List<Roles> roles = rolesService.obtenerTodos();
            // Convertimos la lista de Entidades a lista de DTOs
            List<RolDTO> rolesDto = roles.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
            
            return ResponseEntity.ok(rolesDto);
        } catch (Exception e) {
            e.printStackTrace(); // Es bueno imprimir el error en consola para depurar
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RolDTO> obtenerPorId(@PathVariable Integer id) {
        try {
            Roles rol = rolesService.obtenerPorId(id);
            return ResponseEntity.ok(mapToDto(rol)); // Devolvemos el DTO
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/nombre/{nombreRol}")
    public ResponseEntity<RolDTO> obtenerPorNombre(@PathVariable String nombreRol) {
        try {
            Roles rol = rolesService.obtenerPorNombre(nombreRol);
            return ResponseEntity.ok(mapToDto(rol)); // Devolvemos el DTO
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping
    public ResponseEntity<RolDTO> crear(@RequestBody Roles rol) {
        try {
            Roles nuevoRol = rolesService.guardar(rol);
            return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(nuevoRol));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RolDTO> actualizar(@PathVariable Integer id, @RequestBody Roles rol) {
        try {
            Roles rolActualizado = rolesService.actualizar(id, rol);
            return ResponseEntity.ok(mapToDto(rolActualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            rolesService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}