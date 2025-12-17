package ista.M4A2.models.dao;

import org.springframework.data.repository.CrudRepository;

import ista.M4A2.models.entity.PersonaPaciente;

public interface IPersonaPacienteDao extends CrudRepository<PersonaPaciente, Long>{

	PersonaPaciente findByCorreo(String correo);
	
	PersonaPaciente findByResetToken(String token);

}
