package ista.M4A2.models.services.serv;

import java.util.List;

import ista.M4A2.models.entity.ContactoEmergencia;

public interface IContactoEmergenciaService {

	public List<ContactoEmergencia> findAll();
	
	public ContactoEmergencia save(ContactoEmergencia contactoEmergencia);
	
	public ContactoEmergencia findById(Long id);
	
	public void delete(Long id);
	
}
