package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.SistemaSugerenciasDao;
import ista.M4A2.models.entity.SistemaSugerencias;
import ista.M4A2.models.services.serv.SistemaSugerenciasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SistemaSugerenciasServiceImpl implements SistemaSugerenciasService {
    
    @Autowired
    private SistemaSugerenciasDao sugerenciasDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<SistemaSugerencias> obtenerTodos() {
        return sugerenciasDao.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public SistemaSugerencias obtenerPorId(Integer id) {
        return sugerenciasDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Sugerencia no encontrada con ID: " + id));
    }
    
    @Override
    @Transactional
    public SistemaSugerencias guardar(SistemaSugerencias sugerencia) {
        return sugerenciasDao.save(sugerencia);
    }
    
    @Override
    @Transactional
    public SistemaSugerencias actualizar(Integer id, SistemaSugerencias sugerencia) {
        SistemaSugerencias sugerenciaExistente = obtenerPorId(id);
        sugerenciaExistente.setTituloSugerencias(sugerencia.getTituloSugerencias());
        sugerenciaExistente.setDetalleSugerencias(sugerencia.getDetalleSugerencias());
        sugerenciaExistente.setLinkImagen(sugerencia.getLinkImagen());
        return sugerenciasDao.save(sugerenciaExistente);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        SistemaSugerencias sugerencia = obtenerPorId(id);
        sugerenciasDao.delete(sugerencia);
    }
}