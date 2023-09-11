package it.uniroma3.siwcatalog.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwcatalog.model.Fornitore;

public interface FornitoreRepository extends CrudRepository<Fornitore,Long>{

    public boolean existsByNomeFornitoreAndEmailFornitore(String nomeFornitore, String emailFornitore);

}
