package ista.M4A2.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ista.M4A2.models.dao.IContenedorLecheDao;
import ista.M4A2.models.entity.ContenedorLeche;
import ista.M4A2.models.services.serv.IContenedorLecheService;

@Service
public class ContenedorLecheSeviceImpl implements IContenedorLecheService {

	@Autowired
	private IContenedorLecheDao ContenedorLecheDao;
	
	@Override
	public List<ContenedorLeche> findAll() {
		return (List<ContenedorLeche>) ContenedorLecheDao.findAll();
	}

	@Override
	public ContenedorLeche save(ContenedorLeche contenedorLeche) {
		return ContenedorLecheDao.save(contenedorLeche);
	}

	@Override
	public ContenedorLeche findById(Long id) {
		return ContenedorLecheDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		ContenedorLecheDao.deleteById(id);
	}

}
