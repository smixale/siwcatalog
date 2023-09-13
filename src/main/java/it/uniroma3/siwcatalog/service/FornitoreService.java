package it.uniroma3.siwcatalog.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwcatalog.model.Fornitore;
import it.uniroma3.siwcatalog.model.Prodotto;
import it.uniroma3.siwcatalog.repository.FornitoreRepository;
import it.uniroma3.siwcatalog.repository.ProdottoRepository;
import jakarta.transaction.Transactional;

@Service
public class FornitoreService {
    
    @Autowired
    private FornitoreRepository fornitoreRepository;

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private ProdottoService prodottoService;

    @Transactional
    public Iterable <Fornitore> findAllFornitori(){
        return this.fornitoreRepository.findAll();
    }

    @Transactional
    public void creaFornitore(Fornitore fornitore){
        if(fornitore.getIndirizzo()==null){
            fornitore.setIndirizzo("indirizzo sconosciuto");
        }
        this.fornitoreRepository.save(fornitore);
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
    public Fornitore addProdotto (Long fornitoreId, Long prodottoId){

        Fornitore fornitore = this.fornitoreRepository.findById(fornitoreId).get();
        Prodotto prodotto = this.prodottoRepository.findById(prodottoId).get();

        Set<Prodotto> prodotti = fornitore.getProdottiForniti();
        prodotti.add(prodotto);
        fornitore.setProdottiForniti(prodotti);
        return this.fornitoreRepository.save(fornitore);
    }

    @Transactional
    public Fornitore removeProdotto (Long fornitoreId, Long prodottoId){
        
        Fornitore fornitore = this.fornitoreRepository.findById(fornitoreId).get();
        Prodotto prodotto = this.prodottoRepository.findById(prodottoId).get();

        Set <Prodotto> prodotti = fornitore.getProdottiForniti();
        prodotti.remove (prodotto);
        fornitore.setProdottiForniti(prodotti);

        return this.fornitoreRepository.save(fornitore);
    }

    @Transactional
    public void deleteFornitore (Long id){
        Fornitore f = this.fornitoreRepository.findById(id).get();

        this.fornitoreRepository.deleteById(this.rimuoviProdotti(id));
    }

    /* funzione di supporto che rimuove il fornitore da i prodotti */
    private Long rimuoviProdotti(Long id){
        Set<Prodotto> prodotti = this.fornitoreRepository.findById(id).get().getProdottiForniti();
        for (Prodotto prodotto : prodotti) {
            this.prodottoService.removeFornitore(prodotto.getId(), id);
            this.prodottoRepository.save(prodotto);
        }
        this.fornitoreRepository.findById(id);
        return id;
    }
}
