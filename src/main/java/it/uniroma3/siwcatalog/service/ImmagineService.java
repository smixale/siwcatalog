package it.uniroma3.siwcatalog.service;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siwcatalog.model.Immagine;
import it.uniroma3.siwcatalog.repository.ImmaginiRepository;

public class ImmagineService {
    
    @Autowired
    private ImmaginiRepository immaginiRepository;

    public void salvaImmagine(Immagine immagine){
        this.immaginiRepository.save(immagine);
    }

    public Immagine findImmagineById (Long id){
        return this.immaginiRepository.findById(id).get();
    }
}
