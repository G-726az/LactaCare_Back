package ista.M4A2.models.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ista.M4A2.models.dao.ICubiculosDao;
import ista.M4A2.models.entity.Cubiculos;
import ista.M4A2.models.services.serv.ICubiculosService;

@Service
public class CubiculosServiceImpl implements ICubiculosService {

    @Autowired
    private ICubiculosDao cubiculosDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cubiculos> findAll() {
        return (List<Cubiculos>) cubiculosDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cubiculos findById(Long id) {
        return cubiculosDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Cubiculos save(Cubiculos cubiculo) {
        return cubiculosDao.save(cubiculo);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cubiculosDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cubiculos> findBySalaLactanciaId(Long idLactario) {
        return cubiculosDao.findBySalaLactanciaId(idLactario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cubiculos> findByEstado(String estado) {
        return cubiculosDao.findByEstadoCb(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cubiculos> findDisponiblesBySala(Long idLactario, String estado) {
        return cubiculosDao.findDisponiblesBySala(idLactario, estado);
    }
}