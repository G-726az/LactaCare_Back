package ista.M4A2.models.services.serv;

import java.util.List;

import ista.M4A2.models.entity.ContenedorLeche;

public interface IContenedorLecheService {
	
	public List<ContenedorLeche> findAll();
	
	public ContenedorLeche save(ContenedorLeche contenedorLeche);
	
	public ContenedorLeche findById(Long id);
	
	public void delete(Long id);

}
