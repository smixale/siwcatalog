package it.uniroma3.siwcatalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwcatalog.model.Fornitore;
import it.uniroma3.siwcatalog.repository.FornitoreRepository;

@Service
public class FornitoreService {
    
    @Autowired
    private FornitoreRepository fornitoreRepository;

    public Iterable <Fornitore> findAllFornitori(){
        return this.fornitoreRepository.findAll();
    }

    public void saveFornitore(Fornitore fornitore){
        this.fornitoreRepository.save(fornitore);
    }

    public Fornitore findFornitoreById (Long id){
        return this.fornitoreRepository.findById(id).orElse(null);
    }
}
