package ista.M4A2.models.dao;

import ista.M4A2.models.entity.Institucion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitucionDao extends JpaRepository<Institucion, Long> {
    
}
