package it.uniroma3.siwcatalog.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwcatalog.controller.GlobalController;
import it.uniroma3.siwcatalog.model.Commento;
import it.uniroma3.siwcatalog.model.Prodotto;
import it.uniroma3.siwcatalog.repository.CommentiRepository;
import jakarta.transaction.Transactional;


@Service
public class CommentoService {
    
    @Autowired
    private CommentiRepository commentiRepository;

    @Autowired
    private GlobalController globalController;

    @Transactional
    public Commento saveCommento (Commento commento){
        return this.commentiRepository.save(commento);
    }

    @Transactional
    public Commento creaCommento (Commento commento){
        commento.setAutore(this.globalController.getUser().getUsername());
        return this.commentiRepository.save(commento);
    }

    @Transactional
    public Commento findCommentoById(Long id){
        return this.commentiRepository.findById(id).get();
    }

    @Transactional
    public void deleteCommento(Long id){
        this.commentiRepository.delete(this.commentiRepository.findById(id).get());
    }
}
