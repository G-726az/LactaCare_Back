package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.SistemaImagenesDao;
import ista.M4A2.models.entity.SistemaImagenes;
import ista.M4A2.models.services.serv.SistemaImagenesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SistemaImagenesServiceImpl implements SistemaImagenesService {
    
    @Autowired
    private SistemaImagenesDao imagenesDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<SistemaImagenes> obtenerTodos() {
        return imagenesDao.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public SistemaImagenes obtenerPorId(Integer id) {
        return imagenesDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Imagen no encontrada con ID: " + id));
    }
    
    @Override
    @Transactional
    public SistemaImagenes guardar(SistemaImagenes imagen) {
        return imagenesDao.save(imagen);
    }
    
    @Override
    @Transactional
    public SistemaImagenes actualizar(Integer id, SistemaImagenes imagen) {
        SistemaImagenes imagenExistente = obtenerPorId(id);
        imagenExistente.setCategoriaImagenes(imagen.getCategoriaImagenes());
        imagenExistente.setLinkImagenes(imagen.getLinkImagenes());
        return imagenesDao.save(imagenExistente);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        SistemaImagenes imagen = obtenerPorId(id);
        imagenesDao.delete(imagen);
    }
}