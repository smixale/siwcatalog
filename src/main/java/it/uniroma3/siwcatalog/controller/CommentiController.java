package it.uniroma3.siwcatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwcatalog.model.Commento;
import it.uniroma3.siwcatalog.model.Prodotto;
import it.uniroma3.siwcatalog.service.CommentoService;
import it.uniroma3.siwcatalog.service.ProdottoService;
import jakarta.validation.Valid;


@Controller
public class CommentiController {

    @Autowired
    private CommentoService commentoService;

    @Autowired
    private ProdottoService prodottoService;

    @Autowired
    private GlobalController globalController;
    
    /* mapping dalla pagina prodotto per creare un commento relativo al prodotto stesso */
    @PostMapping("/addCommento/{id}")
    public String addCommentoToProdotto(@Valid @ModelAttribute("commento") Commento commento, BindingResult bindingResult, Model model,@PathVariable("id") Long id){

        //commento.setAutore(this.globalController.getUser().getUsername());
        Prodotto prodotto = this.prodottoService.findProdottoById(id);
        
        this.prodottoService.addCommento(prodotto,this.commentoService.saveCommento(commento));

        model.addAttribute("prodotto", prodotto);
        model.addAttribute("commento", new Commento());
        return "prodotto.html";
    }

    @GetMapping("/removeCommento/{prodottoId}/{commentoId}")
    public String removeCommentoToProdotto(@PathVariable("prodottoId") Long prodottoId, @PathVariable("commentoId") Long commentoId, Model model) {

        model.addAttribute("prodotto", this.prodottoService.removeCommento(prodottoId, commentoId));
        this.commentoService.deleteCommento(commentoId);
        model.addAttribute("commento", new Commento());
        return "prodotto.html";
    }
    
}
