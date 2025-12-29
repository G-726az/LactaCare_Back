package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.IDocumentosChatBotDao;
import ista.M4A2.models.entity.DocumentosChatBot;
import ista.M4A2.models.services.serv.IDocumentosChatBotService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class DocumentosChatBotServiceImpl implements IDocumentosChatBotService {

    @Autowired
    private IDocumentosChatBotDao documentosDao;

    @Override
    @Transactional
    public DocumentosChatBot guardar(MultipartFile archivo) throws IOException {
        DocumentosChatBot doc = new DocumentosChatBot();
        doc.setNombreArchivo(archivo.getOriginalFilename());
        doc.setContenido(archivo.getBytes()); 
        return documentosDao.save(doc);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentosChatBot> listarTodos() {
        return (List<DocumentosChatBot>) documentosDao.findAll();
    }

    // --- ESTA ES LA CORRECCIÓN CLAVE PARA EL ERROR 500 AL VER ---
    @Override
    @Transactional(readOnly = true)
    public DocumentosChatBot buscarPorId(Long id) {
        // Usamos orElse(null) para que no explote si no encuentra el ID
        return documentosDao.findById(id).orElse(null);
    }

    // --- ESTA ES LA CORRECCIÓN PARA EL ERROR 500 AL ELIMINAR ---
    @Override
    @Transactional
    public void eliminar(Long id) {
        documentosDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public String obtenerTextoUnificadoDeTodosLosPDFs() {
        StringBuilder textoCompleto = new StringBuilder();
        List<DocumentosChatBot> listaDocs = (List<DocumentosChatBot>) documentosDao.findAll();

        if (listaDocs.isEmpty()) {
            return "No hay manuales cargados.";
        }
        
        textoCompleto.append("INFORMACIÓN EXTRAÍDA DE MANUALES PDF:\n");

        for (DocumentosChatBot doc : listaDocs) {
            textoCompleto.append("\n--- DOC: ").append(doc.getNombreArchivo()).append(" ---\n");
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(doc.getContenido());
                try (PDDocument pdfDocument = PDDocument.load(bis)) {
                    PDFTextStripper stripper = new PDFTextStripper();
                    textoCompleto.append(stripper.getText(pdfDocument));
                }
            } catch (Exception e) {
                textoCompleto.append("[Error leyendo archivo]");
            }
            textoCompleto.append("\n--- FIN DOC ---\n");
        }
        return textoCompleto.toString();
    }
}