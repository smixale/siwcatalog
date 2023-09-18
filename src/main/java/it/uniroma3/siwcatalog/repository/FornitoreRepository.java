package it.uniroma3.siwcatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwcatalog.model.Fornitore;
import it.uniroma3.siwcatalog.model.Prodotto;

import org.springframework.data.repository.query.Param;


public interface FornitoreRepository extends CrudRepository<Fornitore,Long>{

    public boolean existsByNomeFornitoreAndEmailFornitore(String nomeFornitore, String emailFornitore);

	/* metodo che prende l'id di un prodotto e restituisce tutti i fornitori che non forniscono quel prodotto */
		@Query(value="select * "
			+ "from fornitore f "
			+ "where f.id not in "
			+ "(select fornitori_id "
			+ "from prodotto_fornitori "
			+ "where prodotto_fornitori.prodotti_forniti_id = :prodottoId)", nativeQuery=true)
	public Iterable<Fornitore> findFornitoriNotInProdotto( @Param("prodottoId") Long id);

	public List<Fornitore> findByNomeFornitore(String nome);
}
