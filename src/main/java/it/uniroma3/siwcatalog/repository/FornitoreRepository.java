package it.uniroma3.siwcatalog.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwcatalog.model.Fornitore;

public interface FornitoreRepository extends CrudRepository<Fornitore,Long>{

    public boolean existsByNomeFornitoreAndEmailFornitore(String nomeFornitore, String emailFornitore);

    /* 
    	@Query(value="select * "
			+ "from prodotto a "
			+ "where a.id not in "
			+ "(select prodotti_forniti_id "
			+ "from fornitore_prodotto "
			+ "where fornitore_prodotti_forniti.starred_movies_id = :prodottoId)", nativeQuery=true)
	public Iterable<Prodotto> findProdottiNotInFornitore( @Param("prodottoId") Long id);*/
}
