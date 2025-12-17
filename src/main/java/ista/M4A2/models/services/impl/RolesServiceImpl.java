package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.RolesDao;
import ista.M4A2.models.entity.Roles;
import ista.M4A2.models.services.serv.RolesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {
    
    @Autowired
    private RolesDao rolesDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Roles> obtenerTodos() {
        return rolesDao.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Roles obtenerPorId(Integer id) {
        return rolesDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public Roles obtenerPorNombre(String nombreRol) {
        return rolesDao.findByNombreRol(nombreRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con nombre: " + nombreRol));
    }
    
    @Override
    @Transactional
    public Roles guardar(Roles rol) {
        return rolesDao.save(rol);
    }
    
    @Override
    @Transactional
    public Roles actualizar(Integer id, Roles rol) {
        Roles rolExistente = obtenerPorId(id);
        rolExistente.setNombreRol(rol.getNombreRol());
        return rolesDao.save(rolExistente);
    }
    
    @Override
    @Transactional
    public void eliminar(Integer id) {
        Roles rol = obtenerPorId(id);
        rolesDao.delete(rol);
    }
}