package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.*;
import ista.M4A2.models.entity.*;
import ista.M4A2.models.services.serv.IInstitucionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class InstitucionServiceImpl implements IInstitucionService {
    @Autowired private InstitucionDao institucionDao;
    
    @Override 
    @Transactional(readOnly = true) 
    public List<Institucion> findAll() { 
    	return institucionDao.findAll(); }
    
    @Override 
    @Transactional 
    public Institucion save(Institucion institucion) { 
    	return institucionDao.save(institucion); }
    @Override 
    @Transactional(readOnly = true) 
    public Institucion findById(Long id) { 
    	return institucionDao.findById(id).orElse(null); }
    @Override 
    @Transactional 
    public void deleteById(Long id) { 
    	institucionDao.deleteById(id); }
}