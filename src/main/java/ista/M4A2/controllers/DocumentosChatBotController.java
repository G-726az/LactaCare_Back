package ista.M4A2.controllers;

import ista.M4A2.models.entity.DocumentosChatBot;
import ista.M4A2.models.services.serv.IDocumentosChatBotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin(originPatterns = "*")
public class DocumentosChatBotController {

    @Autowired
    private IDocumentosChatBotService documentosService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> subirArchivo(@RequestPart("archivo") MultipartFile archivo) {
        Map<String, Object> response = new HashMap<>();

        if (archivo.isEmpty()) {
            response.put("mensaje", "Error: No has seleccionado ningún archivo.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            DocumentosChatBot documentoGuardado = documentosService.guardar(archivo);
            
            response.put("mensaje", "¡Archivo subido con éxito!");
            response.put("nombre", documentoGuardado.getNombreArchivo());
            response.put("id", documentoGuardado.getIdDocumento());
            
            return new ResponseEntity<>(response, HttpStatus.CREATED);
            
        } catch (Exception e) {
            response.put("mensaje", "Error al subir el archivo a la base de datos.");
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public List<DocumentosChatBot> listar() {
        return documentosService.listarTodos();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            documentosService.eliminar(id);
            response.put("mensaje", "Documento eliminado correctamente.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("mensaje", "Error al eliminar el documento.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
