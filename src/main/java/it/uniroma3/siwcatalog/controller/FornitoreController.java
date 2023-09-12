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
import it.uniroma3.siwcatalog.model.Prodotto;
import it.uniroma3.siwcatalog.service.FornitoreService;
import it.uniroma3.siwcatalog.service.ProdottoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class FornitoreController {

    @Autowired
    private FornitoreService fornitoreService;

    @Autowired
    private FornitoreValidator fornitoreValidator;

    @Autowired
    private ProdottoService prodottoService;

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

    /*mappinng da dornitore{id} alla form di aggiornamento dell fornitore stesso */
    @GetMapping("/formUpdateFornitore/{id}")
    public String vaiFormUpdateFornitore(@PathVariable("id") Long id,Model model) {
        Fornitore fornitore= this.fornitoreService.findFornitoreById(id);
        model.addAttribute("fornitore", fornitore);
        model.addAttribute("prodotti", this.prodottoService.findAllProdotti());
        return "formUpdateFornitore.html";
    }
    
    /* mapping da la form di aggirnamento di un fornitore per aggoungergli prodotti */
    @GetMapping("/addProdotto/{fornitoreId}/{prodottoId}")
    public String addProdottoToFornitore (@PathVariable("fornitoreId") Long fornitoreId, @PathVariable("prodottoId") Long prodottoId,Model model) {

        
        this.prodottoService.addFornitore(prodottoId, fornitoreId);

        model.addAttribute("fornitore", this.fornitoreService.addProdotto(fornitoreId,prodottoId));
        model.addAttribute("prodotti", this.prodottoService.findAllProdotti());
        return "formUpdateFornitore.html";
    }

    @GetMapping(value="/removeProdotto/{fornitoreId}/{prodottoId}")
    public String removeProdotto (@PathVariable("fornitoreId") Long fornitoreId, @PathVariable("prodottoId") Long prodottoId,Model model) {
        Fornitore fornitore = this.fornitoreService.findFornitoreById(fornitoreId);
        Prodotto prodotto = this.prodottoService.findProdottoById(prodottoId);

        this.prodottoService.removeFornitore(prodottoId, fornitoreId);

        model.addAttribute("fornitore", this.fornitoreService.removeProdotto(fornitoreId,prodottoId));
        model.addAttribute("prodotti", this.prodottoService.findAllProdotti());

        return "formUpdateFornitore.html";
    }
    
    
}
