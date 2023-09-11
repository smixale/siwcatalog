package it.uniroma3.siwcatalog.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwcatalog.model.Immagine;
import it.uniroma3.siwcatalog.model.Prodotto;
import it.uniroma3.siwcatalog.repository.ImmaginiRepository;
import it.uniroma3.siwcatalog.repository.ProdottoRepository;

@Service
public class ProdottoService {
    
    @Autowired
    private ProdottoRepository prodottoRepository;
    
    @Autowired
    private ImmaginiRepository immagineRepository;

    /* metodo che riceve separatamente un immagine in forma di MuiltipartFile e un prodotto privo di immagine e assegna ad esso l'immagine ricevuta per poi salvare e restituire il prodotto ora completo */
    public Prodotto creaProdotto(Prodotto prodotto, MultipartFile immagine) throws IOException{
        Immagine immagineProdotto = new Immagine(immagine.getBytes());
        this.immagineRepository.save(immagineProdotto);
        prodotto.setImmagineProdotto(immagineProdotto);
        if (prodotto.getDescrizione()==null) {
            prodotto.setDescrizione("Nessuna descrizione...");
        }
        return this.prodottoRepository.save(prodotto);
    }

    public Iterable <Prodotto> findAllProdotti(){
        return this.prodottoRepository.findAll();
    }

    public Prodotto findProdottoById(Long id){
        return this.prodottoRepository.findById(id).orElse(null);
    }
}
