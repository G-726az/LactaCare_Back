package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.*;
import ista.M4A2.models.entity.*;
import ista.M4A2.models.services.serv.IDiasLaborablesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class Dias_LaborablesServiceImpl implements IDiasLaborablesService {
	@Autowired 
	private Dias_LaborablesDao diasLaborablesDao;
    @Override 
    @Transactional(readOnly = true) 
    public List<Dias_Laborables> findAll() { 
    	return diasLaborablesDao.findAll(); }
    @Override 
    @Transactional 
    public Dias_Laborables save(Dias_Laborables diasLaborables) { 
    	return diasLaborablesDao.save(diasLaborables); }
    @Override 
    @Transactional(readOnly = true) 
    public Dias_Laborables findById(Long id) { 
    	return diasLaborablesDao.findById(id).orElse(null); }
    @Override 
    @Transactional 
    public void deleteById(Long id) { 
    	diasLaborablesDao.deleteById(id); }
}
