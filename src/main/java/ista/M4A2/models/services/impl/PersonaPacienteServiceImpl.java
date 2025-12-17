package ista.M4A2.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ista.M4A2.models.dao.IPersonaPacienteDao;
import ista.M4A2.models.entity.PersonaPaciente;
import ista.M4A2.models.services.serv.IPersonaPacienteService;

@Service
public class PersonaPacienteServiceImpl implements IPersonaPacienteService {

	@Autowired
	private IPersonaPacienteDao PersonaPacienteDao;
	
	@Override
	@Transactional (readOnly = true)
	public List<PersonaPaciente> findAll() {
		return (List<PersonaPaciente>) PersonaPacienteDao.findAll();
	}

	@Override
	@Transactional
	public PersonaPaciente save(PersonaPaciente personaPaciente) {
		return PersonaPacienteDao.save(personaPaciente);
	}

	@Override
	@Transactional (readOnly = true)
	public PersonaPaciente findById(Long id) {
		return PersonaPacienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		PersonaPacienteDao.deleteById(id);
		
	}

	@Override
	public PersonaPaciente findByCorreo(String correo) {
		return PersonaPacienteDao.findByCorreo(correo);
	}

	@Override
	public PersonaPaciente findByResetToken(String token) {
		return PersonaPacienteDao.findByResetToken(token);
	}

}
