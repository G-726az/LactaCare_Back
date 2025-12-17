package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.*;
import ista.M4A2.models.entity.*;
import ista.M4A2.models.services.serv.IUbicacion_ContenedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class Ubicacion_ContenedorServiceImpl implements IUbicacion_ContenedorService {
	@Autowired
	private Ubicacion_ContenedorDao ubicacionContenedorDao;

	@Override
	@Transactional(readOnly = true)
	public List<Ubicacion_Contenedor> findAll() {
		return ubicacionContenedorDao.findAll();
	}

	@Override
	@Transactional
	public Ubicacion_Contenedor save(Ubicacion_Contenedor ubicacionContenedor) {
		return ubicacionContenedorDao.save(ubicacionContenedor);
	}

	@Override
	@Transactional(readOnly = true)
	public Ubicacion_Contenedor findById(Long id) {
		return ubicacionContenedorDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		ubicacionContenedorDao.deleteById(id);
	}
}