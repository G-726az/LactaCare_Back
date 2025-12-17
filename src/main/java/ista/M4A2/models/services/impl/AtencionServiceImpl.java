package ista.M4A2.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ista.M4A2.models.dao.IAtencionDao;
import ista.M4A2.models.entity.Atencion;
import ista.M4A2.models.services.serv.IAtencionService;

@Service
public class AtencionServiceImpl implements IAtencionService {

	@Autowired
	private IAtencionDao AtencionDao;
	
	@Override
	public List<Atencion> findAll() {
		return (List<Atencion>) AtencionDao.findAll();
	}

	@Override
	public Atencion save(Atencion Atencion) {
		return AtencionDao.save(Atencion);
	}

	@Override
	public Atencion findById(Long id) {
		return AtencionDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		AtencionDao.deleteById(id);
	}
	
}
