package it.uniroma3.siwcatalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwcatalog.model.Commento;
import it.uniroma3.siwcatalog.repository.CommentiRepository;
import jakarta.transaction.Transactional;


@Service
public class CommentoService {
    
    @Autowired
    private CommentiRepository commentiRepository;

    @Transactional
    public Commento saveCommento (Commento commento){
        return this.commentiRepository.save(commento);
    }

    public Commento findCommentoById(Long id){
        return this.commentiRepository.findById(id).get();
    }

    public void deleteCommento(Long id){
        this.commentiRepository.delete(this.commentiRepository.findById(id).get());
    }
}
