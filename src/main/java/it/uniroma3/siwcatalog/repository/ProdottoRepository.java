package it.uniroma3.siwcatalog.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwcatalog.model.Fornitore;
import it.uniroma3.siwcatalog.model.Prodotto;
import org.springframework.data.repository.query.Param;


public interface ProdottoRepository extends CrudRepository<Prodotto,Long>{

    /* metodo che prende l'id di un prodotto e restituisce la lista di tutti i fornitori che non forniscono quel prodotto */
    /*
    	@Query(value="select * "
				+ "from fornitore a "
				+ "where a.id not in "
				+ "(select prodotto_fornitori "
				+ "from fornitore_prodotto "
				+ "where fornitore_prodotti_forniti.starred_movies_id = :prodottoId)", nativeQuery=true)
	public Iterable<Fornitore> findFornitoriNotInProdotto( @Param("prodottoId") Long id);*/
    
    public boolean existsByNomeProdottoAndPrezzo(String nomeProdotto,Float prezzo);

	public List<Prodotto> findByNomeProdotto(String nome);
}
