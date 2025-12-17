package ista.M4A2.models.dao;

import ista.M4A2.models.entity.SistemaImagenes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaImagenesDao extends JpaRepository<SistemaImagenes, Integer> {
}