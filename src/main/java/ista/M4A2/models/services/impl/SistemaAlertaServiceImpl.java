package ista.M4A2.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ista.M4A2.models.dao.SistemaAlertaDao;
import ista.M4A2.models.entity.SistemaAlerta;
import ista.M4A2.models.services.serv.ISistemaAlertaService;

@Service
public class SistemaAlertaServiceImpl implements ISistemaAlertaService {

	@Autowired
	private SistemaAlertaDao sistemaAlertaDao;
	
	@Override
	public List<SistemaAlerta> findAll() {
		return (List<SistemaAlerta>) sistemaAlertaDao.findAll();
	}

	@Override
	public List<SistemaAlerta> findByTipoAlerta(String tipoAlerta) {
		return sistemaAlertaDao.findByTipoAlerta(tipoAlerta);
	}

	@Override
	public SistemaAlerta save(SistemaAlerta sistemaAlerta) {
		return sistemaAlertaDao.save(sistemaAlerta);
	}

	@Override
	public SistemaAlerta findById(Long id) {
		return sistemaAlertaDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		sistemaAlertaDao.deleteById(id);
	}

}
