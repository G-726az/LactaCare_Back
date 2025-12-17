package ista.M4A2.models.services.serv;

import java.util.List;

import ista.M4A2.models.entity.Ubicacion_Contenedor;

public interface IUbicacion_ContenedorService {
	public List<Ubicacion_Contenedor> findAll();
    public Ubicacion_Contenedor save(Ubicacion_Contenedor ubicacionContenedor);
    public Ubicacion_Contenedor findById(Long id);
    public void deleteById(Long id);
}
