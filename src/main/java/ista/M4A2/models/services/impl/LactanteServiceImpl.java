package ista.M4A2.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ista.M4A2.models.dao.ILactanteDao;
import ista.M4A2.models.entity.Lactante;
import ista.M4A2.models.services.serv.ILactanteService;

@Service
public class LactanteServiceImpl implements ILactanteService {

	@Autowired
	private ILactanteDao LactanteDao;
	
	@Override
	public List<Lactante> findAll() {
		return (List<Lactante>) LactanteDao.findAll();
	}

	@Override
	public Lactante save(Lactante lactante) {
		return LactanteDao.save(lactante);
	}

	@Override
	public Lactante findById(Long id) {
		return LactanteDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		LactanteDao.deleteById(id);
	}
	
}
