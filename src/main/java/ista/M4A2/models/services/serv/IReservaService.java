package ista.M4A2.models.services.serv;

import java.util.List;

import ista.M4A2.models.entity.Reserva;

public interface IReservaService {

	public List<Reserva> findAll();
	
	public Reserva save(Reserva contactoEmergencia);
	
	public Reserva findById(Long id);
	
	public void delete(Long id);
	
}
