package it.uniroma3.siwcatalog.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwcatalog.controller.validator.FornitoreValidator;
import it.uniroma3.siwcatalog.model.Fornitore;
import it.uniroma3.siwcatalog.service.FornitoreService;
import jakarta.validation.Valid;

@Controller
public class FornitoreController {

    @Autowired
    private FornitoreService fornitoreService;

    @Autowired
    private FornitoreValidator fornitoreValidator;

    /* mapping dal'index per la lafina formNewFornitore */
    @GetMapping("/formNewFornitore")
    public String formNewFornitore(Model model){
        model.addAttribute("fornitore", new Fornitore());
        return "formNewFornitore.html";
    }
    
    /* mapping dalla pagina formNewFornitore per la pagina nel dettaglio del fornitore appena inserito */
    @PostMapping("/fornitore")
    public String listaFornitori(Model model, @Valid @ModelAttribute("fornitore") Fornitore fornitore, BindingResult bindingResult) throws IOException{
        this.fornitoreValidator.validate(fornitore, bindingResult);
        if (!bindingResult.hasErrors()) {            
            this.fornitoreService.saveFornitore(fornitore);              //il save della repository salva la variabile nel database
            model.addAttribute("fornitori", fornitore);         //model.addAttribute restituisce alla pagina mappata la seconda variabile con il nome specificato
            return "fornitore.html";
        }else{
            return "erroreFornitore.html";
        }
    }

    /* mapping dall'index per la lista di tutti i fornitori */
    @GetMapping("/listaFornitori")
    public String getListaFornitori(Model model){
        model.addAttribute("fornitori",  this.fornitoreService.findAllFornitori());
        return "listaFornitori.html";
    }

    /* mapping da la pagina listaFornitori per il fornitore nel dettaglio */
   @GetMapping("/fornitore/{id}")
	public String getStrategia(@PathVariable("id") Long id, Model model) {
		Fornitore fornitore= this.fornitoreService.findFornitoreById(id);
        model.addAttribute("fornitore", fornitore);
		return "fornitore.html";
	}
}
