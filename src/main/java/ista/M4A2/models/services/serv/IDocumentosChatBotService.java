package ista.M4A2.models.services.serv;

import ista.M4A2.models.entity.DocumentosChatBot;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface IDocumentosChatBotService {
    
    public DocumentosChatBot guardar(MultipartFile archivo) throws IOException;
    
    // Listar todos los documentos (sin el contenido pesado, solo nombres e IDs)
    public List<DocumentosChatBot> listarTodos();
    
    // Buscar uno por ID
    public DocumentosChatBot buscarPorId(Long id);
    
    // Eliminar
    public void eliminar(Long id);
    
    // --- MÉTODO CLAVE PARA LA IA ---
    // Extrae el texto de TODOS los PDFs guardados y lo une en un solo String
    public String obtenerTextoUnificadoDeTodosLosPDFs();
}