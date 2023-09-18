package it.uniroma3.siwcatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwcatalog.model.Fornitore;
import it.uniroma3.siwcatalog.model.Prodotto;
import org.springframework.data.repository.query.Param;


public interface ProdottoRepository extends CrudRepository<Prodotto,Long>{

	/* metodo che prende l'id di un fornitore e restituisce tutti i prodotti che non sono forniti da quel fornitore */
		@Query(value="select * "
			+ "from prodotto p "
			+ "where p.id not in "
			+ "(select prodotti_forniti_id "
			+ "from prodotto_fornitori "
			+ "where prodotto_fornitori.fornitori_id = :fornitoreId)", nativeQuery=true)
	public Iterable<Prodotto> findProdottiNotInFornitore( @Param("fornitoreId") Long id);
    
    public boolean existsByNomeProdottoAndPrezzo(String nomeProdotto,Float prezzo);

	public List<Prodotto> findByNomeProdotto(String nome);
}
