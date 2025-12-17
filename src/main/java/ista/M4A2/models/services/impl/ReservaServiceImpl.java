package ista.M4A2.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ista.M4A2.models.dao.IReservaDao;
import ista.M4A2.models.entity.Reserva;
import ista.M4A2.models.services.serv.IReservaService;

@Service
public class ReservaServiceImpl implements IReservaService {

	@Autowired
	private IReservaDao ReservaDao;
	
	@Override
	public List<Reserva> findAll() {
		return (List<Reserva>) ReservaDao.findAll();
	}

	@Override
	public Reserva save(Reserva Reserva) {
		return ReservaDao.save(Reserva);
	}

	@Override
	public Reserva findById(Long id) {
		return ReservaDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		ReservaDao.deleteById(id);
	}
	
}
