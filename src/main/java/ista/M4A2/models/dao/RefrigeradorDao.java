package ista.M4A2.models.dao;

import ista.M4A2.models.entity.Refrigerador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefrigeradorDao extends JpaRepository<Refrigerador, Long> {
    
}
