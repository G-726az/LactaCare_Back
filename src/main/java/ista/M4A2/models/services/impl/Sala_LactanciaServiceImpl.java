package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.*;
import ista.M4A2.models.entity.*;
import ista.M4A2.models.services.serv.ISala_LactanciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class Sala_LactanciaServiceImpl implements ISala_LactanciaService {
	@Autowired
	private Sala_LactanciaDao lactarioDao;

	@Override
    @Transactional(readOnly = true) 
    public List<Sala_Lactancia> findAll() { return lactarioDao.findAll(); }

    @Override
    @Transactional 
    public Sala_Lactancia save(Sala_Lactancia lactario) {
    	return lactarioDao.save(lactario); }

    @Override
    @Transactional(readOnly = true) 
    public Sala_Lactancia findById(Long id) { 
    	return lactarioDao.findById(id).orElse(null); }
 
    @Override
    @Transactional 
    public void deleteById(Long id) { 
    	lactarioDao.deleteById(id); }
}
