package it.uniroma3.siwcatalog.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwcatalog.model.Fornitore;
import it.uniroma3.siwcatalog.model.Prodotto;

import org.springframework.data.repository.query.Param;


public interface FornitoreRepository extends CrudRepository<Fornitore,Long>{

    public boolean existsByNomeFornitoreAndEmailFornitore(String nomeFornitore, String emailFornitore);
/*
    	@Query(value="select * "
				+ "from prodotto a "
				+ "where a.id not in "
				+ "(select prodotto_fornitori "
				+ "from fornitore_prodotto "
				+ "where fornitore_prodotti_forniti.starred_movies_id = :prodottoId)", nativeQuery=true)
	public Iterable<Prodotto> findProdottiNotInFornitore( @Param("prodottoId") Long id);*/

	public List<Fornitore> findByNomeFornitore(String nome);
}
