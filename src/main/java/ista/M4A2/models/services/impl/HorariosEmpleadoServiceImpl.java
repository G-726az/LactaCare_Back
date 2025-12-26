package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.HorariosEmpleadoDao;
import ista.M4A2.models.entity.HorariosEmpleado;
import ista.M4A2.models.services.serv.HorariosEmpleadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HorariosEmpleadoServiceImpl implements HorariosEmpleadoService {
    
    @Autowired
    private HorariosEmpleadoDao horariosEmpleadoDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<HorariosEmpleado> obtenerTodos() {
        return horariosEmpleadoDao.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public HorariosEmpleado obtenerPorId(Integer id) {
        return horariosEmpleadoDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario de empleado no encontrado con ID: " + id));
    }
    
    @Override
    @Transactional
    public HorariosEmpleado guardar(HorariosEmpleado horario) {
        return horariosEmpleadoDao.save(horario);
    }
    
    @Override
    @Transactional
    public HorariosEmpleado actualizar(Integer id, HorariosEmpleado horario) {
        HorariosEmpleado horarioExistente = obtenerPorId(id);
        horarioExistente.setHoraInicioJornada(horario.getHoraInicioJornada());
        horarioExistente.setHoraFinJornada(horario.getHoraFinJornada());
        horarioExistente.setHoraInicioDescanso(horario.getHoraInicioDescanso());
        horarioExistente.setHoraFinDescanso(horario.getHoraFinDescanso());
        return horariosEmpleadoDao.save(horarioExistente);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        HorariosEmpleado horario = obtenerPorId(id);
        horariosEmpleadoDao.delete(horario);
    }
}