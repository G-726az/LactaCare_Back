package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.HorariosSalaDao;
import ista.M4A2.models.entity.HorariosSala;
import ista.M4A2.models.services.serv.HorariosSalaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HorariosSalaServiceImpl implements HorariosSalaService {
    
    @Autowired
    private HorariosSalaDao horariosSalaDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<HorariosSala> obtenerTodos() {
        return horariosSalaDao.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public HorariosSala obtenerPorId(Integer id) {
        return horariosSalaDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario de sala no encontrado con ID: " + id));
    }
    
    @Override
    @Transactional
    public HorariosSala guardar(HorariosSala horario) {
        return horariosSalaDao.save(horario);
    }
    
    @Override
    @Transactional
    public HorariosSala actualizar(Integer id, HorariosSala horario) {
        HorariosSala horarioExistente = obtenerPorId(id);
        horarioExistente.setHoraApertura(horario.getHoraApertura());
        horarioExistente.setHoraCierre(horario.getHoraCierre());
        horarioExistente.setHoraInicioDescanso(horario.getHoraInicioDescanso());
        horarioExistente.setHoraFinDescanso(horario.getHoraFinDescanso());
        return horariosSalaDao.save(horarioExistente);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        HorariosSala horario = obtenerPorId(id);
        horariosSalaDao.delete(horario);
    }
}