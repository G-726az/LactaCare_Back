package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.DiasLaborablesSalaDao;
import ista.M4A2.models.entity.DiasLaborablesSala;
import ista.M4A2.models.services.serv.DiasLaborablesSalaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiasLaborablesSalaServiceImpl implements DiasLaborablesSalaService {
    
    @Autowired
    private DiasLaborablesSalaDao diasLaborablesSalaDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<DiasLaborablesSala> obtenerTodos() {
        return diasLaborablesSalaDao.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public DiasLaborablesSala obtenerPorId(Long id) {
        return diasLaborablesSalaDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Días laborables de sala no encontrados con ID: " + id));
    }
    
    @Override
    @Transactional
    public DiasLaborablesSala guardar(DiasLaborablesSala dias) {
        return diasLaborablesSalaDao.save(dias);
    }
    
    @Override
    @Transactional
    public DiasLaborablesSala actualizar(Long id, DiasLaborablesSala dias) {
        DiasLaborablesSala diasExistentes = obtenerPorId(id);
        diasExistentes.setDiaLunes(dias.getDiaLunes());
        diasExistentes.setDiaMartes(dias.getDiaMartes());
        diasExistentes.setDiaMiercoles(dias.getDiaMiercoles());
        diasExistentes.setDiaJueves(dias.getDiaJueves());
        diasExistentes.setDiaViernes(dias.getDiaViernes());
        diasExistentes.setDiaSabado(dias.getDiaSabado());
        diasExistentes.setDiaDomingo(dias.getDiaDomingo());
        return diasLaborablesSalaDao.save(diasExistentes);
    }
    
    @Override
    @Transactional
    public void eliminar(Long id) {
        DiasLaborablesSala dias = obtenerPorId(id);
        diasLaborablesSalaDao.delete(dias);
    }
}