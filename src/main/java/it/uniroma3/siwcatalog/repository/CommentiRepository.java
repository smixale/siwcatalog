package it.uniroma3.siwcatalog.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwcatalog.model.Commento;

public interface CommentiRepository extends CrudRepository<Commento,Long>{
    
}
