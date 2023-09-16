package it.uniroma3.siwcatalog.service;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwcatalog.controller.GlobalController;
import it.uniroma3.siwcatalog.model.Commento;
import it.uniroma3.siwcatalog.model.Fornitore;
import it.uniroma3.siwcatalog.model.Immagine;
import it.uniroma3.siwcatalog.model.Prodotto;
import it.uniroma3.siwcatalog.repository.CommentiRepository;
import it.uniroma3.siwcatalog.repository.FornitoreRepository;
import it.uniroma3.siwcatalog.repository.ImmaginiRepository;
import it.uniroma3.siwcatalog.repository.ProdottoRepository;
import jakarta.transaction.Transactional;

@Service
public class ProdottoService {
    
    @Autowired
    private ProdottoRepository prodottoRepository;
    
    @Autowired
    private ImmaginiRepository immagineRepository;

    @Autowired
    private CommentiRepository commentiRepository;

    @Autowired
    private FornitoreRepository fornitoreRepository;

    @Autowired
    private GlobalController globalController;

    /* metodo che riceve separatamente un immagine in forma di MuiltipartFile e un prodotto privo di immagine e assegna ad esso l'immagine ricevuta per poi salvare e restituire il prodotto ora completo */
    @Transactional
    public Prodotto creaProdotto(Prodotto prodotto, MultipartFile immagine) throws IOException{
        Immagine immagineProdotto = new Immagine(immagine.getBytes());
        this.immagineRepository.save(immagineProdotto);
        prodotto.setImmagineProdotto(immagineProdotto);
        if (prodotto.getDescrizione().isEmpty()) {
            prodotto.setDescrizione("Nessuna descrizione...");
        }
        return this.prodottoRepository.save(prodotto);
    }

    @Transactional
    public Iterable <Prodotto> findAllProdotti(){
        return this.prodottoRepository.findAll();
    }

    public Prodotto findProdottoById(Long id){
        return this.prodottoRepository.findById(id).get();
    }

    @Transactional
    public boolean existsById(Long id){
        return this.prodottoRepository.existsById(id);
    }

    @Transactional
    public Prodotto addFornitore(Long prodottoId, Long fornitoreId){

        Fornitore fornitore = this.fornitoreRepository.findById(fornitoreId).get();
        Prodotto prodotto = this.prodottoRepository.findById(prodottoId).get();

        Set <Fornitore> fornitori = prodotto.getFornitori();
        fornitori.add(fornitore);
        prodotto.setFornitori(fornitori);

        return this.prodottoRepository.save(prodotto);
    }

    @Transactional
    public Prodotto removeFornitore (Long prodottoId, Long fornitoreId){

        Fornitore fornitore = this.fornitoreRepository.findById(fornitoreId).get();
        Prodotto prodotto = this.prodottoRepository.findById(prodottoId).get();

        Set <Fornitore> fornitori = prodotto.getFornitori();
        fornitori.remove (fornitore);
        prodotto.setFornitori(fornitori);

        return this.prodottoRepository.save(prodotto);
    }

    @Transactional
    public void addCommento (Prodotto prodotto,Commento commento){
        Set<Commento> commenti = prodotto.getCommenti();
        commenti.add(commento);
        prodotto.setCommenti(commenti);
        this.prodottoRepository.save(prodotto);
    }

    public Prodotto addCommento(Long prodottoId,Long commentoId){
        Prodotto prodotto = this.prodottoRepository.findById(prodottoId).get();
        Commento commento = this.commentiRepository.findById(commentoId).get();

        Set<Commento> commenti = prodotto.getCommenti();
        commenti.add(commento);
        prodotto.setCommenti(commenti);
        return this.prodottoRepository.save(prodotto);
    }

    public Prodotto removeCommento(Long prodottoId,Long commentoId){

        Prodotto prodotto = this.prodottoRepository.findById(prodottoId).get();
        Commento commento = this.commentiRepository.findById(commentoId).get();

        Set<Commento> commenti = prodotto.getCommenti();
        commenti.remove(commento);
        prodotto.setCommenti(commenti);
        return this.prodottoRepository.save(prodotto);
    }

    @Transactional
    public boolean commentato (Long prodottoId){
        return this.verifica(this.findProdottoById(prodottoId));
    }

    /*verifica se l'utente attualmente loggato ha già lasciato un commento al prodotto richiesto */
    private boolean verifica(Prodotto prodotto){
        Set<Commento> commenti = prodotto.getCommenti();
        if (this.globalController.getUser()==null) {
            return false;
        }
        String username = this.globalController.getUser().getUsername();
        for (Commento c : commenti) {
            if (c.getAutore().equals(username)) {
                return false;
            }
        }
        return true;
    }

    @Transactional
    public void deleteProdotto (Long id){
        //this.prodottoRepository.deleteById(this.rimuoviFornitore(id));
        this.prodottoRepository.deleteById(id);
    }

    /* funzione di supporto che rimuove il fornitore da i prodotti */
    private Long rimuoviFornitore(Long id){
        Set<Fornitore> fornitori = this.prodottoRepository.findById(id).get().getFornitori();
        for (Fornitore fornitore : fornitori) {
            this.removeFornitore(id, fornitore.getId());
            this.fornitoreRepository.save(fornitore);
        }
        this.fornitoreRepository.findById(id);
        return id;
    }
}
