package ista.M4A2.models.services.impl;

import ista.M4A2.models.dao.*;
import ista.M4A2.models.entity.*;
import ista.M4A2.models.services.serv.ISala_LactanciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class Sala_LactanciaServiceImpl implements ISala_LactanciaService {
    
    @Autowired
    private Sala_LactanciaDao lactarioDao;
    
    @Autowired
    private ICubiculosDao cubiculosDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Sala_Lactancia> findAll() {
        List<Sala_Lactancia> salas = lactarioDao.findAll();
        // Inicializar SOLO lo necesario para evitar LazyInitializationException
        salas.forEach(sala -> {
            // Inicializar Institucion (solo nombre, NO la lista de salas)
            if (sala.getInstitucion() != null) {
                sala.getInstitucion().getIdInstitucion();
                sala.getInstitucion().getNombreInstitucion();
                // NO tocar salas_lactancia
            }
            
            // Inicializar Horario
            if (sala.getHorarioSala() != null) {
                sala.getHorarioSala().getIdHorarioSala();
                sala.getHorarioSala().getHoraApertura();
            }
            
            // Inicializar Días Laborables
            if (sala.getDiasLaborablesSala() != null) {
                sala.getDiasLaborablesSala().getIdDiaLaborableSala();
                sala.getDiasLaborablesSala().getDiaLunes();
            }
            
            // Inicializar Cubículos
            if (sala.getCubiculos() != null) {
                sala.getCubiculos().size();
                sala.getCubiculos().forEach(c -> {
                    c.getId();
                    c.getNombreCb();
                    c.getEstadoCb();
                });
            }
        });
        return salas;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Sala_Lactancia> findAllActive() {
        List<Sala_Lactancia> salas = lactarioDao.findAllActive();
        // Inicializar relaciones
        salas.forEach(this::initializeRelations);
        return salas;
    }
    
    @Override
    @Transactional
    public Sala_Lactancia save(Sala_Lactancia lactario) {
        if (lactario.getIdLactario() == null && lactario.getEstado() == null) {
            lactario.setEstado("ACTIVO");
        }
        return lactarioDao.save(lactario);
    }
    
    @Override
    @Transactional
    public Sala_Lactancia saveConCubiculos(Sala_Lactancia salaLactancia, int numeroCubiculos) {
        // Establecer estado ACTIVO por defecto si es nuevo
        if (salaLactancia.getIdLactario() == null && salaLactancia.getEstado() == null) {
            salaLactancia.setEstado("ACTIVO");
        }
        
        // 1. Guardar primero la sala de lactancia
        Sala_Lactancia salaGuardada = lactarioDao.save(salaLactancia);
        
        // 2. Crear los cubículos automáticamente
        if (numeroCubiculos > 0) {
            List<Cubiculos> cubiculosCreados = new ArrayList<>();
            
            for (int i = 1; i <= numeroCubiculos; i++) {
                Cubiculos cubiculo = new Cubiculos();
                cubiculo.setNombreCb("Cubículo " + i);
                cubiculo.setEstadoCb("DISPONIBLE");
                cubiculo.setSalaLactancia(salaGuardada);
                
                Cubiculos cubiculoGuardado = cubiculosDao.save(cubiculo);
                cubiculosCreados.add(cubiculoGuardado);
            }
            
            salaGuardada.setCubiculos(cubiculosCreados);
        }
        
        // Inicializar relaciones
        initializeRelations(salaGuardada);
        return salaGuardada;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Sala_Lactancia findById(Long id) {
        Sala_Lactancia sala = lactarioDao.findById(id).orElse(null);
        if (sala != null) {
            initializeRelations(sala);
        }
        return sala;
    }
    
    @Override
    @Transactional
    public void deleteById(Long id) {
        lactarioDao.deleteById(id);
    }
    
    @Override
    @Transactional
    public void softDeleteById(Long id) {
        Sala_Lactancia sala = lactarioDao.findById(id).orElse(null);
        if (sala != null) {
            sala.setEstado("INACTIVO");
            lactarioDao.save(sala);
        }
    }
    
    @Override
    @Transactional
    public void activateById(Long id) {
        Sala_Lactancia sala = lactarioDao.findById(id).orElse(null);
        if (sala != null) {
            sala.setEstado("ACTIVO");
            lactarioDao.save(sala);
        }
    }
    
    /**
     * Inicializa SOLO las relaciones necesarias, evitando lazy collections problemáticas
     */
    private void initializeRelations(Sala_Lactancia sala) {
        try {
            // Inicializar Institucion (SOLO campos simples)
            if (sala.getInstitucion() != null) {
                sala.getInstitucion().getIdInstitucion();
                sala.getInstitucion().getNombreInstitucion();
                // NO acceder a salas_lactancia de Institucion
            }
            
            // Inicializar Horario
            if (sala.getHorarioSala() != null) {
                sala.getHorarioSala().getIdHorarioSala();
                sala.getHorarioSala().getHoraApertura();
                sala.getHorarioSala().getHoraCierre();
                sala.getHorarioSala().getHoraInicioDescanso();
                sala.getHorarioSala().getHoraFinDescanso();
            }
            
            // Inicializar Días Laborables
            if (sala.getDiasLaborablesSala() != null) {
                sala.getDiasLaborablesSala().getIdDiaLaborableSala();
                sala.getDiasLaborablesSala().getDiaLunes();
                sala.getDiasLaborablesSala().getDiaMartes();
                sala.getDiasLaborablesSala().getDiaMiercoles();
                sala.getDiasLaborablesSala().getDiaJueves();
                sala.getDiasLaborablesSala().getDiaViernes();
                sala.getDiasLaborablesSala().getDiaSabado();
                sala.getDiasLaborablesSala().getDiaDomingo();
            }
            
            // Inicializar Cubículos
            if (sala.getCubiculos() != null) {
                sala.getCubiculos().size();
                sala.getCubiculos().forEach(cubiculo -> {
                    cubiculo.getId();
                    cubiculo.getNombreCb();
                    cubiculo.getEstadoCb();
                });
            }
        } catch (Exception e) {
            System.err.println("Error inicializando relaciones de sala ID " + 
                sala.getIdLactario() + ": " + e.getMessage());
        }
    }
}