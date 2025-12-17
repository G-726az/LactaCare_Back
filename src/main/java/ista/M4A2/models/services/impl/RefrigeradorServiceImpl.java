package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.*;
import ista.M4A2.models.entity.*;
import ista.M4A2.models.services.serv.IRefrigeradorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RefrigeradorServiceImpl implements IRefrigeradorService {
	
	@Autowired 
	private RefrigeradorDao refrigeradorDao;
    
	@Override 
    @Transactional(readOnly = true) 
    public List<Refrigerador> findAll() { 
    	return refrigeradorDao.findAll(); }
    
    @Override 
    @Transactional 
    public Refrigerador save(Refrigerador refrigerador) { 
    	return refrigeradorDao.save(refrigerador); }
    
    @Override 
    @Transactional(readOnly = true) 
    public Refrigerador findById(Long id) { 
    	return refrigeradorDao.findById(id).orElse(null); }
    
    @Override
    @Transactional 
    public void deleteById(Long id) { 
    	refrigeradorDao.deleteById(id); }
    
}
