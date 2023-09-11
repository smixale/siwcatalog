package it.uniroma3.siwcatalog.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwcatalog.model.Prodotto;

public interface ProdottoRepository extends CrudRepository<Prodotto,Long>{
    
    public boolean existsByNomeProdottoAndPrezzo(String nomeProdotto,Float prezzo);
}
