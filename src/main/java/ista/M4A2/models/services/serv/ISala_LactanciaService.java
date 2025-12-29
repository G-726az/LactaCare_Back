package ista.M4A2.models.services.serv;

import java.util.List;
import ista.M4A2.models.entity.Sala_Lactancia;

public interface ISala_LactanciaService {
    public List<Sala_Lactancia> findAll();
    public List<Sala_Lactancia> findAllActive();
    public Sala_Lactancia save(Sala_Lactancia lactario);
    public Sala_Lactancia saveConCubiculos(Sala_Lactancia salaLactancia, int numeroCubiculos);
    public Sala_Lactancia findById(Long id);
    public void deleteById(Long id);
    public void softDeleteById(Long id);
    public void activateById(Long id);
}