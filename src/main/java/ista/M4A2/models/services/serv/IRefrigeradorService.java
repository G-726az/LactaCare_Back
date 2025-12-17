package ista.M4A2.models.services.serv;

import java.util.List;
import ista.M4A2.models.entity.Refrigerador;

public interface IRefrigeradorService {
	public List<Refrigerador> findAll();
    public Refrigerador save(Refrigerador refrigerador);
    public Refrigerador findById(Long id);
    public void deleteById(Long id);
}
