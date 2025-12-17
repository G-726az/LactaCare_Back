package ista.M4A2.models.services.serv;

import java.util.List;

import ista.M4A2.models.entity.PersonaPaciente;

public interface IPersonaPacienteService {
	
	public List<PersonaPaciente> findAll();
	
	public PersonaPaciente save(PersonaPaciente personaPaciente);
	
	public PersonaPaciente findById(Long id);
	
	public PersonaPaciente findByCorreo(String correo);
	
	public PersonaPaciente findByResetToken(String token);
	
	public void delete(Long id);
	
}
