package it.uniroma3.siwcatalog.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwcatalog.model.Fornitore;
import it.uniroma3.siwcatalog.model.Prodotto;
import it.uniroma3.siwcatalog.repository.FornitoreRepository;
import jakarta.transaction.Transactional;

@Service
public class FornitoreService {
    
    @Autowired
    private FornitoreRepository fornitoreRepository;

    @Transactional
    public Iterable <Fornitore> findAllFornitori(){
        return this.fornitoreRepository.findAll();
    }

    @Transactional
    public void saveFornitore(Fornitore fornitore){
        this.fornitoreRepository.save(fornitore);
    }

    @Transactional
    public Fornitore findFornitoreById (Long id){
        return this.fornitoreRepository.findById(id).get();
    }

    @Transactional
    public Fornitore addProdotto (Fornitore fornitore, Prodotto prodotto){

        /*Set <Prodotto> prodotti = fornitore.getProdottiForniti();
        prodotti.add (prodotto);*/
    
        fornitore.getProdottiForniti().add(prodotto);
        return fornitore;
    }

    @Transactional
    public Fornitore removeProdotto (Fornitore fornitore, Prodotto prodotto){

        Set <Prodotto> prodotti = fornitore.getProdottiForniti();
        prodotti.remove (prodotto);
        return fornitore;
    }
}
