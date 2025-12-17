package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.EmpleadoHorariosDao;
import ista.M4A2.models.dao.HorariosDao;
import ista.M4A2.models.dao.PersonaEmpleadoDao;
import ista.M4A2.models.entity.EmpleadoHorarios;
import ista.M4A2.models.entity.EmpleadoHorariosPK;
import ista.M4A2.models.entity.Horarios;
import ista.M4A2.models.entity.PersonaEmpleado;
import ista.M4A2.models.services.serv.EmpleadoHorariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpleadoHorariosServiceImpl implements EmpleadoHorariosService {
    
    @Autowired
    private EmpleadoHorariosDao empleadoHorariosDao;
    
    @Autowired
    private PersonaEmpleadoDao empleadoDao;
    
    @Autowired
    private HorariosDao horariosDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<EmpleadoHorarios> obtenerTodos() {
        return empleadoHorariosDao.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public EmpleadoHorarios obtenerPorId(EmpleadoHorariosPK id) {
        return empleadoHorariosDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada con ID: " + id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EmpleadoHorarios> obtenerPorEmpleado(Integer idEmpleado) {
        return empleadoHorariosDao.findByIdEmpleado(idEmpleado);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<EmpleadoHorarios> obtenerPorHorario(Integer idHorario) {
        return empleadoHorariosDao.findByIdHorario(idHorario);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existeAsignacion(Integer idEmpleado, Integer idHorario) {
        return empleadoHorariosDao.existsByEmpleadoAndHorario(idEmpleado, idHorario);
    }
    
    @Override
    @Transactional
    public EmpleadoHorarios asignar(Integer idEmpleado, Integer idHorario) {
        if (existeAsignacion(idEmpleado, idHorario)) {
            throw new RuntimeException("La asignación ya existe");
        }
        
        PersonaEmpleado empleado = empleadoDao.findById(idEmpleado)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + idEmpleado));
        
        Horarios horario = horariosDao.findById(idHorario)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + idHorario));
        
        EmpleadoHorarios empleadoHorarios = new EmpleadoHorarios(empleado, horario);
        return empleadoHorariosDao.save(empleadoHorarios);
    }
    
    @Override
    @Transactional
    public void eliminar(EmpleadoHorariosPK id) {
        EmpleadoHorarios empleadoHorarios = obtenerPorId(id);
        empleadoHorariosDao.delete(empleadoHorarios);
    }
    
    @Override
    @Transactional
    public void eliminarAsignacion(Integer idEmpleado, Integer idHorario) {
        EmpleadoHorariosPK id = new EmpleadoHorariosPK(idEmpleado, idHorario);
        eliminar(id);
    }
}