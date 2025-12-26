package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.DiasLaborablesEmpleadoDao;
import ista.M4A2.models.entity.DiasLaborablesEmpleado;
import ista.M4A2.models.services.serv.DiasLaborablesEmpleadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiasLaborablesEmpleadoServiceImpl implements DiasLaborablesEmpleadoService {
    
    @Autowired
    private DiasLaborablesEmpleadoDao diasLaborablesEmpleadoDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<DiasLaborablesEmpleado> obtenerTodos() {
        return diasLaborablesEmpleadoDao.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public DiasLaborablesEmpleado obtenerPorId(Long id) {
        return diasLaborablesEmpleadoDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Días laborables de empleado no encontrados con ID: " + id));
    }
    
    @Override
    @Transactional
    public DiasLaborablesEmpleado guardar(DiasLaborablesEmpleado dias) {
        return diasLaborablesEmpleadoDao.save(dias);
    }
    
    @Override
    @Transactional
    public DiasLaborablesEmpleado actualizar(Long id, DiasLaborablesEmpleado dias) {
        DiasLaborablesEmpleado diasExistentes = obtenerPorId(id);
        diasExistentes.setDiaLunes(dias.getDiaLunes());
        diasExistentes.setDiaMartes(dias.getDiaMartes());
        diasExistentes.setDiaMiercoles(dias.getDiaMiercoles());
        diasExistentes.setDiaJueves(dias.getDiaJueves());
        diasExistentes.setDiaViernes(dias.getDiaViernes());
        diasExistentes.setDiaSabado(dias.getDiaSabado());
        diasExistentes.setDiaDomingo(dias.getDiaDomingo());
        return diasLaborablesEmpleadoDao.save(diasExistentes);
    }
    
    @Override
    @Transactional
    public void eliminar(Long id) {
        DiasLaborablesEmpleado dias = obtenerPorId(id);
        diasLaborablesEmpleadoDao.delete(dias);
    }
}