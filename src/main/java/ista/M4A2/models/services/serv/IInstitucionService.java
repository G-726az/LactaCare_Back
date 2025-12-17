package ista.M4A2.models.services.serv;

import java.util.List;
import ista.M4A2.models.entity.Institucion;

public interface IInstitucionService {
	public List<Institucion> findAll();
    public Institucion save(Institucion institucion);
    public Institucion findById(Long id);
    public void deleteById(Long id);
}
