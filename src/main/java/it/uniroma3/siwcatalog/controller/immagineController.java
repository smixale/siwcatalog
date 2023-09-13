package it.uniroma3.siwcatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;



import it.uniroma3.siwcatalog.model.Immagine;
import it.uniroma3.siwcatalog.service.ImmagineService;


@Controller
public class immagineController {

    @Autowired
    private ImmagineService immagineService;

    @GetMapping("/immagineProdotto/{id}")
    public ResponseEntity<byte[]> displayItemImage(@PathVariable("id") Long id) {
        Immagine img = this.immagineService.findImmagineById(id);
        byte[] image = img.getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
    
}
