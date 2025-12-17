package ista.M4A2.models.services.serv;

import java.util.List;

import ista.M4A2.models.entity.Lactante;

public interface ILactanteService {

	public List<Lactante> findAll();
	
	public Lactante save(Lactante contactoEmergencia);
	
	public Lactante findById(Long id);
	
	public void delete(Long id);
	
}
