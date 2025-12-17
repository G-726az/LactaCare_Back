package ista.M4A2.models.services.serv;

import java.util.List;

import ista.M4A2.models.entity.Estado_Refrigerador;

public interface IEstado_RefrigeradorService {
	public List<Estado_Refrigerador> findAll();
    public Estado_Refrigerador save(Estado_Refrigerador estadoRefrigerador);
    public Estado_Refrigerador findById(Long id);
    public void deleteById(Long id);
}
