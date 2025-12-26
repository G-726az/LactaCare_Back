package ista.M4A2.models.dao;

import ista.M4A2.models.entity.HorariosSala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorariosSalaDao extends JpaRepository<HorariosSala, Integer> {
}