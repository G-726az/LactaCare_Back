package ista.M4A2.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ista.M4A2.models.entity.SistemaAlerta;

public interface SistemaAlertaDao extends CrudRepository<SistemaAlerta, Long>{

	List<SistemaAlerta> findByTipoAlerta(String tipoAlerta);

}
