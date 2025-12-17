package ista.M4A2.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ista.M4A2.models.dao.IContactoEmergenciaDao;
import ista.M4A2.models.entity.ContactoEmergencia;
import ista.M4A2.models.services.serv.IContactoEmergenciaService;

@Service
public class ContactoEmergenciaServiceImpl implements IContactoEmergenciaService {

	@Autowired
	private IContactoEmergenciaDao contactoEmergenciaDao;
	
	@Override
	public List<ContactoEmergencia> findAll() {
		return (List<ContactoEmergencia>) contactoEmergenciaDao.findAll();
	}

	@Override
	public ContactoEmergencia save(ContactoEmergencia contactoEmergencia) {
		return contactoEmergenciaDao.save(contactoEmergencia);
	}

	@Override
	public ContactoEmergencia findById(Long id) {
		return contactoEmergenciaDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		contactoEmergenciaDao.deleteById(id);
	}
	
}
