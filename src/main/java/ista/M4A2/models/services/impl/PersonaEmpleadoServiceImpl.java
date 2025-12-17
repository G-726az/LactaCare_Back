package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.HorariosDao;
import ista.M4A2.models.dao.PersonaEmpleadoDao;
import ista.M4A2.models.entity.Horarios;
import ista.M4A2.models.entity.PersonaEmpleado;
import ista.M4A2.models.services.serv.PersonaEmpleadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonaEmpleadoServiceImpl implements PersonaEmpleadoService {
    
    @Autowired
    private PersonaEmpleadoDao empleadoDao;
    
    @Autowired
    private HorariosDao horariosDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<PersonaEmpleado> obtenerTodos() {
        return empleadoDao.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public PersonaEmpleado obtenerPorId(Integer id) {
        return empleadoDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public PersonaEmpleado obtenerPorCedula(String cedula) {
        return empleadoDao.findByCedula(cedula)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con cédula: " + cedula));
    }
    
    @Override
    @Transactional(readOnly = true)
    public PersonaEmpleado obtenerPorCorreo(String correo) {
        return empleadoDao.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con correo: " + correo));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PersonaEmpleado> obtenerPorRol(Integer idRol) {
        return empleadoDao.findByRolIdRoles(idRol);
    }
    
    @Override
    @Transactional
    public PersonaEmpleado guardar(PersonaEmpleado empleado) {
        return empleadoDao.save(empleado);
    }
    
    @Override
    @Transactional
    public PersonaEmpleado actualizar(Integer id, PersonaEmpleado empleado) {
        PersonaEmpleado empleadoExistente = obtenerPorId(id);
        empleadoExistente.setPerfilEmpleadoImg(empleado.getPerfilEmpleadoImg());
        empleadoExistente.setCedula(empleado.getCedula());
        empleadoExistente.setPrimerNombre(empleado.getPrimerNombre());
        empleadoExistente.setSegundoNombre(empleado.getSegundoNombre());
        empleadoExistente.setPrimerApellido(empleado.getPrimerApellido());
        empleadoExistente.setSegundoApellido(empleado.getSegundoApellido());
        empleadoExistente.setCorreo(empleado.getCorreo());
        empleadoExistente.setTelefono(empleado.getTelefono());
        empleadoExistente.setFechaNacimiento(empleado.getFechaNacimiento());
        empleadoExistente.setRol(empleado.getRol());
        return empleadoDao.save(empleadoExistente);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        PersonaEmpleado empleado = obtenerPorId(id);
        empleadoDao.delete(empleado);
    }
    
    @Override
    @Transactional
    public PersonaEmpleado asignarHorario(Integer idEmpleado, Integer idHorario) {
        PersonaEmpleado empleado = obtenerPorId(idEmpleado);
        Horarios horario = horariosDao.findById(idHorario)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + idHorario));
        
        if (!empleado.getHorarios().contains(horario)) {
            empleado.getHorarios().add(horario);
            return empleadoDao.save(empleado);
        }
        return empleado;
    }
    
    @Override
    @Transactional
    public PersonaEmpleado removerHorario(Integer idEmpleado, Integer idHorario) {
        PersonaEmpleado empleado = obtenerPorId(idEmpleado);
        Horarios horario = horariosDao.findById(idHorario)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + idHorario));
        
        empleado.getHorarios().remove(horario);
        return empleadoDao.save(empleado);
    }
}