package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.SistemaDao;
import ista.M4A2.models.entity.Sistema;
import ista.M4A2.models.services.serv.SistemaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SistemaServiceImpl implements SistemaService {
    
    @Autowired
    private SistemaDao sistemaDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Sistema> obtenerTodos() {
        return sistemaDao.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Sistema obtenerPorId(Integer id) {
        return sistemaDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Sistema no encontrado con ID: " + id));
    }
    
    @Override
    @Transactional
    public Sistema guardar(Sistema sistema) {
        return sistemaDao.save(sistema);
    }
    
    @Override
    @Transactional
    public Sistema actualizar(Integer id, Sistema sistema) {
        Sistema sistemaExistente = obtenerPorId(id);
        sistemaExistente.setLogoSistema(sistema.getLogoSistema());
        sistemaExistente.setNombreSistema(sistema.getNombreSistema());
        sistemaExistente.setEslogan(sistema.getEslogan());
        return sistemaDao.save(sistemaExistente);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        Sistema sistema = obtenerPorId(id);
        sistemaDao.delete(sistema);
    }
}