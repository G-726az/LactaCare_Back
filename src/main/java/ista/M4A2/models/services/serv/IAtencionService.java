package ista.M4A2.models.services.serv;

import java.util.List;

import ista.M4A2.models.entity.Atencion;

public interface IAtencionService {

	public List<Atencion> findAll();
	
	public Atencion save(Atencion contactoEmergencia);
	
	public Atencion findById(Long id);
	
	public void delete(Long id);
	
}
