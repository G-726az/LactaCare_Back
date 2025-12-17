package ista.M4A2.models.dao;

import ista.M4A2.models.entity.DocumentosChatBot;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDocumentosChatBotDao extends CrudRepository<DocumentosChatBot, Long> {
    
}