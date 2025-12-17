package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.*;
import ista.M4A2.models.entity.*;
import ista.M4A2.models.services.serv.IEstado_RefrigeradorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class Estado_RefrigeradorServiceImpl implements IEstado_RefrigeradorService {
	@Autowired
	private Estado_RefrigeradorDao estadoRefrigeradorDao;

	@Override
	@Transactional(readOnly = true)
	public List<Estado_Refrigerador> findAll() {
		return estadoRefrigeradorDao.findAll();
	}

	@Override
	@Transactional
	public Estado_Refrigerador save(Estado_Refrigerador estadoRefrigerador) {
		return estadoRefrigeradorDao.save(estadoRefrigerador);
	}

	@Override
	@Transactional(readOnly = true)
	public Estado_Refrigerador findById(Long id) {
		return estadoRefrigeradorDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		estadoRefrigeradorDao.deleteById(id);
	}
}