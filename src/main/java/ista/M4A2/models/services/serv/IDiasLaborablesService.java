package ista.M4A2.models.services.serv;

import java.util.List;
import ista.M4A2.models.entity.Dias_Laborables;

public interface IDiasLaborablesService {
	public List<Dias_Laborables> findAll();
    public Dias_Laborables save(Dias_Laborables diasLaborables);
    public Dias_Laborables findById(Long id);
    public void deleteById(Long id);
}
