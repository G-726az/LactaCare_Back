package ista.M4A2.models.services.serv;

import java.util.List;

import ista.M4A2.models.entity.SistemaAlerta;

public interface ISistemaAlertaService {
	
	public List<SistemaAlerta> findAll();
	
	public List<SistemaAlerta> findByTipoAlerta(String tipoAlerta);
	
	public SistemaAlerta save(SistemaAlerta sistemaAlerta);
	
	public SistemaAlerta findById(Long id);
	
	public void delete(Long id);
	
}
