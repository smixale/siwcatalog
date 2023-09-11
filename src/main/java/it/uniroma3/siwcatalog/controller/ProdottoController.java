package it.uniroma3.siwcatalog.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import it.uniroma3.siwcatalog.controller.validator.ProdottoValidator;
import it.uniroma3.siwcatalog.model.Fornitore;
import it.uniroma3.siwcatalog.model.Prodotto;
import it.uniroma3.siwcatalog.service.FornitoreService;
import it.uniroma3.siwcatalog.service.ProdottoService;
import jakarta.validation.Valid;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class ProdottoController {

    @Autowired
    private ProdottoService prodottoService;

    @Autowired
    private ProdottoValidator prodottoValidator;

    @Autowired
    private FornitoreService fornitoreService;

    @GetMapping("/formNewProdotto")
    public String getFormNewProdotto(Model model) {
        model.addAttribute("prodotto", new Prodotto());
        return "/formNewProdotto.html";
    }
    
    @PostMapping("prodotto")
    public String getProdotto(@Valid @ModelAttribute("prodotto") Prodotto prodotto, BindingResult bindingResult, Model model, @ModelAttribute("file") MultipartFile immagine) throws IOException {
        this.prodottoValidator.validate(prodotto, bindingResult);
        if (!bindingResult.hasErrors()) {
            model.addAttribute("prodotto", this.prodottoService.creaProdotto(prodotto, immagine));
            return "prodotto.html";
        }else{
            //return "erroreProdotto.html";
            return "/formNewProdotto.html";
        }
        
    }

        /* mapping dall'index per la lista di tutti i prodotti */
    @GetMapping("/listaProdotti")
    public String getListaFornitori(Model model){
        model.addAttribute("prodotti",  this.prodottoService.findAllProdotti());
        return "listaProdotti.html";
    }

  /* mapping da la pagina listaFornitori per il prodotto nel dettaglio */
   @GetMapping("/prodotto/{id}")
	public String getStrategia(@PathVariable("id") Long id, Model model) {
		Prodotto prodotto= this.prodottoService.findProdottoById(id);
        model.addAttribute("prodotto", prodotto);
		return "prodotto.html";
	}

    @GetMapping(value="/formUpdateProdotto/{id}")
    public String formUpdateProdotto (@PathVariable("id") Long id, Model model) {
        Prodotto prodotto = this.prodottoService.findProdottoById(id);

        model.addAttribute("prodotto", prodotto);
        model.addAttribute("fornitori", this.fornitoreService.findAllFornitori());

        return "formUpdateProdotto.html";
    }

    @GetMapping(value="/addFornitore/{prodottoId}/{fornitoreId}")
    public String addFornitore (@PathVariable("prodottoId") Long prodottoId, @PathVariable("fornitoreId") Long fornitoreId, Model model) {

        Prodotto prodotto =this.prodottoService.findProdottoById(prodottoId);
        Fornitore fornitore = this.fornitoreService.findFornitoreById(fornitoreId);

        this.prodottoService.addFornitore(prodotto, fornitore);
        this.fornitoreService.addProdotto(fornitore, prodotto);

        model.addAttribute("prodotto", prodotto);
        model.addAttribute("fornitori", this.fornitoreService.findAllFornitori());

        return "formUpdateProdotto.html";
    }

    @GetMapping(value="/removeFornitore/{prodottoId}/{fornitoreId}")
    public String removeFornitore (@PathVariable("prodottoId") Long prodottoId, @PathVariable("fornitoreId") Long fornitoreId, Model model) {

        Prodotto prodotto =this.prodottoService.findProdottoById(prodottoId);
        Fornitore fornitore = this.fornitoreService.findFornitoreById(fornitoreId);

        this.prodottoService.removeFornitore(prodotto, fornitore);
        this.fornitoreService.removeProdotto(fornitore, prodotto);

        model.addAttribute("prodotto", prodotto);
        model.addAttribute("fornitori", this.fornitoreService.findAllFornitori());
        
        return "formUpdateProdotto.html";
    }
}
