package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.HorariosDao;
import ista.M4A2.models.entity.Horarios;
import ista.M4A2.models.services.serv.HorariosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HorariosServiceImpl implements HorariosService {
    
    @Autowired
    private HorariosDao horariosDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Horarios> obtenerTodos() {
        return horariosDao.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Horarios obtenerPorId(Integer id) {
        return horariosDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + id));
    }
    
    @Override
    @Transactional
    public Horarios guardar(Horarios horario) {
        return horariosDao.save(horario);
    }
    
    @Override
    @Transactional
    public Horarios actualizar(Integer id, Horarios horario) {
        Horarios horarioExistente = obtenerPorId(id);
        horarioExistente.setHoraInicioJornada(horario.getHoraInicioJornada());
        horarioExistente.setHoraFinJornada(horario.getHoraFinJornada());
        horarioExistente.setHoraInicioDescanso(horario.getHoraInicioDescanso());
        horarioExistente.setHoraFinDescanso(horario.getHoraFinDescanso());
        return horariosDao.save(horarioExistente);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        Horarios horario = obtenerPorId(id);
        horariosDao.delete(horario);
    }
}